package com.nextgen.iptv.domain.usecase.provider

import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.data.remote.api.XtreamCodesService
import com.nextgen.iptv.domain.repository.CategoryRepository
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.StreamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

sealed class SyncProgress {
    data object Starting : SyncProgress()
    data class Progress(val message: String) : SyncProgress()
    data object Success : SyncProgress()
    data class Error(val message: String) : SyncProgress()
}

class SyncProviderUseCase @Inject constructor(
    private val providerRepository: ProviderRepository,
    private val categoryRepository: CategoryRepository,
    private val streamRepository: StreamRepository,
    private val xtreamCodesService: XtreamCodesService
) {
    operator fun invoke(providerId: String): Flow<SyncProgress> = flow {
        emit(SyncProgress.Starting)
        
        try {
            val provider = providerRepository.getProviderById(providerId)
                ?: throw IllegalArgumentException("Provider not found")
            
            when (provider.type) {
                "xtream" -> syncXtreamCodes(providerId, provider.serverUrl, provider.username, provider.password, this)
                "m3u_url" -> emit(SyncProgress.Error("M3U URL sync not implemented yet"))
                "m3u_local" -> emit(SyncProgress.Error("M3U Local sync not implemented yet"))
            }
            
            emit(SyncProgress.Success)
        } catch (e: Exception) {
            emit(SyncProgress.Error(e.message ?: "Unknown error"))
        }
    }
    
    private suspend inline fun <T> kotlinx.coroutines.flow.FlowCollector<T>.emit(value: T) {
        this.emit(value)
    }
    
    private suspend fun syncXtreamCodes(
        providerId: String,
        baseUrl: String,
        username: String?,
        password: String?,
        collector: kotlinx.coroutines.flow.FlowCollector<SyncProgress>
    ) {
        if (username == null || password == null) {
            throw IllegalArgumentException("Username and password required for Xtream Codes")
        }
        
        // Create API with the correct base URL
        val api = xtreamCodesService.createApi(baseUrl)
        val apiUrl = XtreamCodesService.buildApiUrl(baseUrl)
        
        collector.emit(SyncProgress.Progress("Verbindung wird hergestellt..."))
        
        // Test connection first
        try {
            val auth = api.authenticate(apiUrl, username, password)
            if (auth.userInfo?.auth != 1) {
                throw IllegalStateException("Authentication failed: ${auth.userInfo?.message ?: "Invalid credentials"}")
            }
        } catch (e: Exception) {
            throw IllegalStateException("Connection failed: ${e.message}")
        }
        
        collector.emit(SyncProgress.Progress("Bereinige alte Daten..."))
        
        // Clear existing data
        categoryRepository.deleteCategoriesByProvider(providerId)
        streamRepository.deleteStreamsByProvider(providerId)
        
        // Sync Live Categories and Streams
        collector.emit(SyncProgress.Progress("Synchronisiere Live TV Kategorien..."))
        val liveCategories = api.getLiveCategories(apiUrl, username, password)
        val categoryEntities = liveCategories.mapNotNull { cat ->
            cat.categoryId?.let {
                CategoryEntity(
                    id = "${providerId}_live_$it",
                    providerId = providerId,
                    name = cat.categoryName ?: "Unknown",
                    type = "live"
                )
            }
        }
        categoryRepository.addCategories(categoryEntities)
        
        collector.emit(SyncProgress.Progress("Synchronisiere Live TV Streams..."))
        val liveStreams = api.getLiveStreams(apiUrl, username, password)
        val streamEntities = liveStreams.mapNotNull { stream ->
            stream.streamId?.let { streamId ->
                StreamEntity(
                    id = "${providerId}_live_$streamId",
                    categoryId = stream.categoryId?.let { "${providerId}_live_$it" } ?: "",
                    providerId = providerId,
                    name = stream.name ?: "Unknown",
                    streamUrl = XtreamCodesService.buildStreamUrl(baseUrl, username, password, streamId.toString()),
                    logoUrl = stream.streamIcon,
                    epgChannelId = stream.epgChannelId,
                    type = "live"
                )
            }
        }
        streamRepository.addStreams(streamEntities)
        
        // Sync VOD
        collector.emit(SyncProgress.Progress("Synchronisiere VOD..."))
        try {
            val vodCategories = api.getVodCategories(apiUrl, username, password)
            val vodCategoryEntities = vodCategories.mapNotNull { cat ->
                cat.categoryId?.let {
                    CategoryEntity(
                        id = "${providerId}_vod_$it",
                        providerId = providerId,
                        name = cat.categoryName ?: "Unknown",
                        type = "vod"
                    )
                }
            }
            categoryRepository.addCategories(vodCategoryEntities)
            
            val vodStreams = api.getVodStreams(apiUrl, username, password)
            val vodEntities = vodStreams.mapNotNull { stream ->
                stream.streamId?.let { streamId ->
                    StreamEntity(
                        id = "${providerId}_vod_$streamId",
                        categoryId = stream.categoryId?.let { "${providerId}_vod_$it" } ?: "",
                        providerId = providerId,
                        name = stream.name ?: "Unknown",
                        streamUrl = XtreamCodesService.buildVodUrl(baseUrl, username, password, streamId.toString()),
                        logoUrl = stream.streamIcon,
                        epgChannelId = null,
                        type = "vod"
                    )
                }
            }
            streamRepository.addStreams(vodEntities)
        } catch (e: Exception) {
            // VOD might not be available for all providers
            collector.emit(SyncProgress.Progress("VOD nicht verfügbar oder Fehler: ${e.message}"))
        }
        
        // Sync Series
        collector.emit(SyncProgress.Progress("Synchronisiere Serien..."))
        try {
            val seriesCategories = api.getSeriesCategories(apiUrl, username, password)
            val seriesCatEntities = seriesCategories.mapNotNull { cat ->
                cat.categoryId?.let {
                    CategoryEntity(
                        id = "${providerId}_series_$it",
                        providerId = providerId,
                        name = cat.categoryName ?: "Unknown",
                        type = "series"
                    )
                }
            }
            categoryRepository.addCategories(seriesCatEntities)
        } catch (e: Exception) {
            // Series might not be available
            collector.emit(SyncProgress.Progress("Serien nicht verfügbar oder Fehler: ${e.message}"))
        }
    }
}
