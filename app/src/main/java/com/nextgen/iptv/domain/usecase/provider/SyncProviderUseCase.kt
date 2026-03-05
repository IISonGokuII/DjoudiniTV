package com.nextgen.iptv.domain.usecase.provider

import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.data.remote.api.XtreamCodesApi
import com.nextgen.iptv.data.remote.dto.XtreamLiveStream
import com.nextgen.iptv.data.remote.dto.XtreamVodStream
import com.nextgen.iptv.domain.repository.CategoryRepository
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.StreamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class SyncProviderUseCase @Inject constructor(
    private val providerRepository: ProviderRepository,
    private val categoryRepository: CategoryRepository,
    private val streamRepository: StreamRepository,
    private val xtreamCodesApi: XtreamCodesApi
) {
    operator fun invoke(providerId: String): Flow<SyncProgress> = flow {
        emit(SyncProgress.Starting)
        
        try {
            val provider = providerRepository.getProviderById(providerId)
                ?: throw IllegalArgumentException("Provider not found")
            
            when (provider.type) {
                "xtream" -> syncXtreamCodes(providerId, provider.serverUrl, provider.username, provider.password)
                "m3u_url" -> emit(SyncProgress.Error("M3U URL sync not implemented yet"))
                "m3u_local" -> emit(SyncProgress.Error("M3U Local sync not implemented yet"))
            }
            
            emit(SyncProgress.Success)
        } catch (e: Exception) {
            emit(SyncProgress.Error(e.message ?: "Unknown error"))
        }
    }
    
    private suspend fun syncXtreamCodes(
        providerId: String,
        baseUrl: String,
        username: String?,
        password: String?
    ) {
        if (username == null || password == null) {
            throw IllegalArgumentException("Username and password required for Xtream Codes")
        }
        
        // Clear existing data
        categoryRepository.deleteCategoriesByProvider(providerId)
        streamRepository.deleteStreamsByProvider(providerId)
        
        // Sync Live Categories and Streams
        val liveCategories = xtreamCodesApi.getLiveCategories(username, password)
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
        
        // Sync Live Streams
        val liveStreams = xtreamCodesApi.getLiveStreams(username, password)
        val streamEntities = liveStreams.mapNotNull { stream ->
            stream.streamId?.let { streamId ->
                StreamEntity(
                    id = "${providerId}_live_$streamId",
                    categoryId = stream.categoryId?.let { "${providerId}_live_$it" } ?: "",
                    providerId = providerId,
                    name = stream.name ?: "Unknown",
                    streamUrl = XtreamCodesApi.buildStreamUrl(baseUrl, username, password, streamId.toString()),
                    logoUrl = stream.streamIcon,
                    epgChannelId = stream.epgChannelId,
                    type = "live"
                )
            }
        }
        streamRepository.addStreams(streamEntities)
    }
    
    sealed class SyncProgress {
        data object Starting : SyncProgress()
        data class Progress(val message: String) : SyncProgress()
        data object Success : SyncProgress()
        data class Error(val message: String) : SyncProgress()
    }
}
