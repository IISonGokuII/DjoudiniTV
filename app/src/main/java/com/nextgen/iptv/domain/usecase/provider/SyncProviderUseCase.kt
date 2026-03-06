package com.nextgen.iptv.domain.usecase.provider

import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.data.local.entity.EpisodeEntity
import com.nextgen.iptv.data.local.entity.SeasonEntity
import com.nextgen.iptv.data.local.entity.SeriesEntity
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.data.remote.api.XtreamCodesApiDynamic
import com.nextgen.iptv.data.remote.api.XtreamCodesService
import com.nextgen.iptv.domain.repository.CategoryRepository
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.SeriesRepository
import com.nextgen.iptv.domain.repository.StreamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import java.util.UUID
import javax.inject.Inject

sealed class SyncProgress {
    data object Starting : SyncProgress()
    data class Progress(val message: String, val percent: Int = 0) : SyncProgress()
    data object Success : SyncProgress()
    data class Error(val message: String) : SyncProgress()
}

class SyncProviderUseCase @Inject constructor(
    private val providerRepository: ProviderRepository,
    private val categoryRepository: CategoryRepository,
    private val streamRepository: StreamRepository,
    private val seriesRepository: SeriesRepository,
    private val xtreamCodesService: XtreamCodesService
) {
    /**
     * Quick sync - only Live TV (fast, 10-30 seconds)
     */
    fun syncQuick(providerId: String): Flow<SyncProgress> = flow {
        emit(SyncProgress.Starting)
        
        try {
            val provider = providerRepository.getProviderById(providerId)
                ?: throw IllegalArgumentException("Provider nicht gefunden")
            
            when (provider.type) {
                "xtream" -> {
                    if (provider.username == null || provider.password == null) {
                        throw IllegalArgumentException("Username und Passwort erforderlich")
                    }
                    syncXtreamCodesQuick(this, providerId, provider.serverUrl, provider.username, provider.password)
                }
                "m3u_url" -> emit(SyncProgress.Error("M3U URL Sync noch nicht implementiert"))
                "m3u_local" -> emit(SyncProgress.Error("M3U Local Sync noch nicht implementiert"))
            }
            
            emit(SyncProgress.Success)
        } catch (e: Exception) {
            emit(SyncProgress.Error(e.message ?: "Unbekannter Fehler"))
        }
    }
    
    /**
     * Full sync - Live TV + VOD + Series (Light version, 2-5 minutes)
     * Note: Details are loaded on-demand when opening movies/series
     */
    operator fun invoke(providerId: String): Flow<SyncProgress> = flow {
        emit(SyncProgress.Starting)
        
        try {
            val provider = providerRepository.getProviderById(providerId)
                ?: throw IllegalArgumentException("Provider nicht gefunden")
            
            when (provider.type) {
                "xtream" -> {
                    if (provider.username == null || provider.password == null) {
                        throw IllegalArgumentException("Username und Passwort erforderlich")
                    }
                    syncXtreamCodesFull(this, providerId, provider.serverUrl, provider.username, provider.password)
                }
                "m3u_url" -> emit(SyncProgress.Error("M3U URL Sync noch nicht implementiert"))
                "m3u_local" -> emit(SyncProgress.Error("M3U Local Sync noch nicht implementiert"))
            }
            
            emit(SyncProgress.Success)
        } catch (e: Exception) {
            emit(SyncProgress.Error(e.message ?: "Unbekannter Fehler"))
        }
    }
    
    /**
     * Sync only VOD categories (fast, for onboarding)
     */
    fun syncVodCategoriesOnly(providerId: String): Flow<SyncProgress> = flow {
        emit(SyncProgress.Starting)
        
        try {
            val provider = providerRepository.getProviderById(providerId)
                ?: throw IllegalArgumentException("Provider nicht gefunden")
            
            if (provider.type != "xtream" || provider.username == null || provider.password == null) {
                emit(SyncProgress.Error("Nur Xtream Codes wird unterstutzt"))
                return@flow
            }
            
            val api = xtreamCodesService.createApi(provider.serverUrl)
            val apiUrl = XtreamCodesService.buildApiUrl(provider.serverUrl)
            
            emit(SyncProgress.Progress("Lade Film-Kategorien...", 0))
            
            // Only sync VOD categories
            val vodCategories = api.getVodCategories(apiUrl, provider.username, provider.password)
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
            
            emit(SyncProgress.Progress("${vodCategories.size} Film-Kategorien geladen", 100))
            emit(SyncProgress.Success)
        } catch (e: Exception) {
            emit(SyncProgress.Error(e.message ?: "Fehler beim Laden der Film-Kategorien"))
        }
    }
    
    /**
     * Sync only Series categories (fast, for onboarding)
     */
    fun syncSeriesCategoriesOnly(providerId: String): Flow<SyncProgress> = flow {
        emit(SyncProgress.Starting)
        
        try {
            val provider = providerRepository.getProviderById(providerId)
                ?: throw IllegalArgumentException("Provider nicht gefunden")
            
            if (provider.type != "xtream" || provider.username == null || provider.password == null) {
                emit(SyncProgress.Error("Nur Xtream Codes wird unterstutzt"))
                return@flow
            }
            
            val api = xtreamCodesService.createApi(provider.serverUrl)
            val apiUrl = XtreamCodesService.buildApiUrl(provider.serverUrl)
            
            emit(SyncProgress.Progress("Lade Serien-Kategorien...", 0))
            
            // Only sync Series categories
            val seriesCategories = api.getSeriesCategories(apiUrl, provider.username, provider.password)
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
            
            emit(SyncProgress.Progress("${seriesCategories.size} Serien-Kategorien geladen", 100))
            emit(SyncProgress.Success)
        } catch (e: Exception) {
            emit(SyncProgress.Error(e.message ?: "Fehler beim Laden der Serien-Kategorien"))
        }
    }
    
    private suspend fun syncXtreamCodesQuick(
        collector: kotlinx.coroutines.flow.FlowCollector<SyncProgress>,
        providerId: String,
        baseUrl: String,
        username: String,
        password: String
    ) {
        val api = xtreamCodesService.createApi(baseUrl)
        val apiUrl = XtreamCodesService.buildApiUrl(baseUrl)
        
        collector.emit(SyncProgress.Progress("Verbindung wird hergestellt...", 5))
        
        // Test connection with timeout
        try {
            withTimeout(10000) {
                val auth = api.authenticate(apiUrl, username, password)
                if (auth.userInfo?.auth != 1) {
                    throw IllegalStateException("Authentifizierung fehlgeschlagen")
                }
            }
        } catch (e: Exception) {
            throw IllegalStateException("Verbindung fehlgeschlagen: ${e.message}")
        }
        
        // Clear old data
        categoryRepository.deleteCategoriesByProvider(providerId)
        streamRepository.deleteStreamsByProvider(providerId)
        
        collector.emit(SyncProgress.Progress("Synchronisiere Live TV...", 20))
        
        // Sync ONLY Live TV (fast - usually under 30 seconds)
        syncLiveTV(api, apiUrl, providerId, baseUrl, username, password, collector)
        
        collector.emit(SyncProgress.Progress("Fertig! VOD und Serien konnen spater synchronisiert werden.", 100))
    }
    
    private suspend fun syncXtreamCodesFull(
        collector: kotlinx.coroutines.flow.FlowCollector<SyncProgress>,
        providerId: String,
        baseUrl: String,
        username: String,
        password: String
    ) {
        val api = xtreamCodesService.createApi(baseUrl)
        val apiUrl = XtreamCodesService.buildApiUrl(baseUrl)
        
        collector.emit(SyncProgress.Progress("Verbindung wird hergestellt...", 5))
        
        // Test connection with timeout
        try {
            withTimeout(10000) {
                val auth = api.authenticate(apiUrl, username, password)
                if (auth.userInfo?.auth != 1) {
                    throw IllegalStateException("Authentifizierung fehlgeschlagen")
                }
            }
        } catch (e: Exception) {
            throw IllegalStateException("Verbindung fehlgeschlagen: ${e.message}")
        }
        
        // Clear old data
        categoryRepository.deleteCategoriesByProvider(providerId)
        streamRepository.deleteStreamsByProvider(providerId)
        
        // Live TV
        collector.emit(SyncProgress.Progress("Synchronisiere Live TV...", 15))
        syncLiveTV(api, apiUrl, providerId, baseUrl, username, password, collector)
        
        // VOD (Light - no individual detail requests)
        collector.emit(SyncProgress.Progress("Synchronisiere Filme...", 40))
        try {
            syncVOD(api, apiUrl, providerId, baseUrl, username, password)
            collector.emit(SyncProgress.Progress("Filme synchronisiert", 65))
        } catch (e: Exception) {
            collector.emit(SyncProgress.Progress("Film-Sync fehlgeschlagen: ${e.message}", 65))
        }
        
        // Series (Light - no seasons/episodes yet)
        collector.emit(SyncProgress.Progress("Synchronisiere Serien...", 70))
        try {
            syncSeries(api, apiUrl, providerId, username, password)
            collector.emit(SyncProgress.Progress("Serien synchronisiert", 90))
        } catch (e: Exception) {
            collector.emit(SyncProgress.Progress("Serien-Sync fehlgeschlagen: ${e.message}", 90))
        }
        
        collector.emit(SyncProgress.Progress("Synchronisation abgeschlossen!", 100))
    }
    
    private suspend fun syncLiveTV(
        api: XtreamCodesApiDynamic,
        apiUrl: String,
        providerId: String,
        baseUrl: String,
        username: String,
        password: String,
        collector: kotlinx.coroutines.flow.FlowCollector<SyncProgress>
    ) {
        // Categories
        collector.emit(SyncProgress.Progress("Lade Kategorien...", 25))
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
        
        // Streams
        collector.emit(SyncProgress.Progress("Lade Kanale...", 50))
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
        
        collector.emit(SyncProgress.Progress("${liveStreams.size} Kanale geladen", 100))
    }
    
    private suspend fun syncVOD(
        api: XtreamCodesApiDynamic,
        apiUrl: String,
        providerId: String,
        baseUrl: String,
        username: String,
        password: String
    ) {
        // Categories
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
        
        // Streams - LIGHT SYNC: Only basic info, no individual API calls for details
        // Details (plot, cast, etc.) will be loaded on-demand when opening the movie
        val vodStreams = api.getVodStreams(apiUrl, username, password)
        val vodEntities = vodStreams.mapNotNull { stream ->
            stream.streamId?.let { streamId ->
                StreamEntity(
                    id = "${providerId}_vod_$streamId",
                    categoryId = stream.categoryId?.let { "${providerId}_vod_$it" } ?: "",
                    providerId = providerId,
                    name = stream.name ?: "Unknown",
                    streamUrl = XtreamCodesService.buildVodUrl(baseUrl, username, password, streamId.toString(), stream.containerExtension ?: "mp4"),
                    logoUrl = stream.streamIcon,
                    epgChannelId = null,
                    type = "vod",
                    rating = stream.rating,
                    rating5Based = stream.rating5Based,
                    added = stream.added,
                    containerExtension = stream.containerExtension
                    // Note: plot, cast, director, etc. will be loaded on-demand
                )
            }
        }
        
        streamRepository.addStreams(vodEntities)
    }
    
    private suspend fun syncSeries(
        api: XtreamCodesApiDynamic,
        apiUrl: String,
        providerId: String,
        username: String,
        password: String
    ) {
        // Categories
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
        
        // Series list - LIGHT SYNC: Only basic info from list endpoint
        // Full details (seasons, episodes) will be loaded on-demand when opening a series
        val seriesList = api.getSeries(apiUrl, username, password)
        
        val seriesEntities = seriesList.mapNotNull { seriesDto ->
            seriesDto.seriesId?.toString()?.let { seriesId ->
                SeriesEntity(
                    id = "${providerId}_series_$seriesId",
                    providerId = providerId,
                    categoryId = seriesDto.categoryId?.let { "${providerId}_series_$it" } ?: "",
                    name = seriesDto.name ?: "Unknown",
                    plot = seriesDto.plot,
                    posterUrl = seriesDto.cover,
                    backdropUrl = seriesDto.backdropPath?.firstOrNull(),
                    rating = seriesDto.rating5Based?.toString() ?: seriesDto.rating,
                    releaseDate = seriesDto.releaseDate,
                    genre = seriesDto.genre,
                    cast = seriesDto.cast,
                    director = seriesDto.director,
                    episodeRunTime = seriesDto.episodeRunTime,
                    totalSeasons = 0, // Will be loaded on-demand
                    totalEpisodes = 0 // Will be loaded on-demand
                    // Note: Seasons and episodes will be loaded on-demand when opening the series
                )
            }
        }
        
        // Save to database
        if (seriesEntities.isNotEmpty()) {
            seriesRepository.insertSeries(seriesEntities)
        }
    }
}
