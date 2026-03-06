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
     * Full sync - Live TV + VOD + Series (can take 5-30 minutes)
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
        
        // VOD
        collector.emit(SyncProgress.Progress("Synchronisiere VOD...", 40))
        try {
            syncVOD(api, apiUrl, providerId, baseUrl, username, password)
        } catch (e: Exception) {
            collector.emit(SyncProgress.Progress("VOD-Sync fehlgeschlagen: ${e.message}", 70))
        }
        
        // Series
        collector.emit(SyncProgress.Progress("Synchronisiere Serien...", 70))
        try {
            syncSeries(api, apiUrl, providerId, baseUrl, username, password)
        } catch (e: Exception) {
            collector.emit(SyncProgress.Progress("Serien-Sync fehlgeschlagen: ${e.message}", 95))
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
        
        // Streams - with detailed info (this can take a while)
        val vodStreams = api.getVodStreams(apiUrl, username, password)
        val vodEntities = mutableListOf<StreamEntity>()
        
        vodStreams.forEach { stream ->
            val streamId = stream.streamId ?: return@forEach
            val vodId = "${providerId}_vod_$streamId"
            
            var vodEntity = StreamEntity(
                id = vodId,
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
            )
            
            // Get detailed info for each VOD
            try {
                val vodInfo = api.getVodInfo(apiUrl, username, password, vodId = streamId.toString())
                val info = vodInfo.info
                
                vodEntity = vodEntity.copy(
                    plot = info?.plot ?: info?.description,
                    cast = info?.cast ?: info?.actors,
                    director = info?.director,
                    genre = info?.genre,
                    rating = info?.rating ?: vodEntity.rating,
                    releaseDate = info?.releaseDate ?: info?.releaseDateAlt,
                    durationSecs = info?.durationSecs,
                    duration = info?.duration,
                    backdropUrl = info?.backdropPath?.firstOrNull(),
                    youtubeTrailer = info?.youtubeTrailer,
                    logoUrl = info?.coverBig ?: info?.movieImage ?: vodEntity.logoUrl
                )
            } catch (e: Exception) {
                // Continue with basic info if detail fetch fails
            }
            
            vodEntities.add(vodEntity)
        }
        
        streamRepository.addStreams(vodEntities)
    }
    
    private suspend fun syncSeries(
        api: XtreamCodesApiDynamic,
        apiUrl: String,
        providerId: String,
        baseUrl: String,
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
        
        // Series list
        val seriesList = api.getSeries(apiUrl, username, password)
        
        val seriesEntities = mutableListOf<SeriesEntity>()
        val seasonEntities = mutableListOf<SeasonEntity>()
        val episodeEntities = mutableListOf<EpisodeEntity>()
        
        seriesList.forEach { seriesDto ->
            val seriesId = seriesDto.seriesId?.toString() ?: return@forEach
            
            val seriesEntity = SeriesEntity(
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
                totalSeasons = 0,
                totalEpisodes = 0
            )
            seriesEntities.add(seriesEntity)
            
            // Get detailed series info for seasons and episodes
            try {
                val seriesInfo = api.getSeriesInfo(apiUrl, username, password, seriesId = seriesId)
                
                // Parse seasons
                seriesInfo.seasons?.forEach { seasonDto ->
                    val seasonId = "${providerId}_series_${seriesId}_season_${seasonDto.seasonNumber}"
                    val seasonEntity = SeasonEntity(
                        id = seasonId,
                        seriesId = seriesEntity.id,
                        seasonNumber = seasonDto.seasonNumber ?: 0,
                        name = seasonDto.name ?: "Staffel ${seasonDto.seasonNumber}",
                        episodeCount = seasonDto.episodeCount ?: 0,
                        posterUrl = seasonDto.coverBig ?: seasonDto.cover
                    )
                    seasonEntities.add(seasonEntity)
                }
                
                // Parse episodes
                seriesInfo.episodes?.forEach { (seasonNumStr, episodes) ->
                    val seasonNumber = seasonNumStr.toIntOrNull() ?: 0
                    
                    episodes.forEach { episodeDto ->
                        val episodeId = episodeDto.id ?: UUID.randomUUID().toString()
                        val seasonId = "${providerId}_series_${seriesId}_season_$seasonNumber"
                        
                        val episodeEntity = EpisodeEntity(
                            id = "${providerId}_episode_$episodeId",
                            seriesId = seriesEntity.id,
                            seasonId = seasonId,
                            episodeNumber = episodeDto.episodeNum ?: 0,
                            seasonNumber = seasonNumber,
                            name = episodeDto.title ?: episodeDto.info?.name ?: "Episode ${episodeDto.episodeNum}",
                            plot = episodeDto.info?.plot,
                            posterUrl = episodeDto.info?.movieImage,
                            durationSec = episodeDto.info?.durationSecs,
                            streamUrl = XtreamCodesService.buildSeriesUrl(
                                baseUrl, username, password, episodeId, episodeDto.containerExtension ?: "mp4"
                            ),
                            containerExtension = episodeDto.containerExtension,
                            added = episodeDto.added,
                            rating = episodeDto.info?.rating
                        )
                        episodeEntities.add(episodeEntity)
                    }
                }
                
                // Update series with counts
                val updatedSeries = seriesEntity.copy(
                    totalSeasons = seriesInfo.seasons?.size ?: 0,
                    totalEpisodes = episodeEntities.count { it.seriesId == seriesEntity.id }
                )
                val index = seriesEntities.indexOf(seriesEntity)
                if (index != -1) {
                    seriesEntities[index] = updatedSeries
                }
                
            } catch (e: Exception) {
                // Continue with next series if one fails
            }
        }
        
        // Save to database
        if (seriesEntities.isNotEmpty()) {
            seriesRepository.insertSeries(seriesEntities)
        }
        if (seasonEntities.isNotEmpty()) {
            seriesRepository.insertSeasons(seasonEntities)
        }
        if (episodeEntities.isNotEmpty()) {
            seriesRepository.insertEpisodes(episodeEntities)
        }
    }
}
