package com.nextgen.iptv.domain.usecase.provider

import com.nextgen.iptv.data.local.entity.CategoryEntity
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
    fun syncQuick(providerId: String): Flow<SyncProgress> = flow {
        emit(SyncProgress.Starting)
        try {
            val provider = providerRepository.getProviderById(providerId) ?: throw IllegalArgumentException("Provider nicht gefunden")
            when (provider.type) {
                "xtream" -> {
                    if (provider.username == null || provider.password == null) throw IllegalArgumentException("Username und Passwort erforderlich")
                    syncXtreamCodesQuick(this, providerId, provider.serverUrl, provider.username, provider.password)
                }
                "m3u_url" -> emit(SyncProgress.Error("M3U URL Sync noch nicht implementiert"))
                "m3u_local" -> emit(SyncProgress.Error("M3U Local Sync noch nicht implementiert"))
            }
            emit(SyncProgress.Success)
        } catch (e: Exception) { emit(SyncProgress.Error(e.message ?: "Unbekannter Fehler")) }
    }

    operator fun invoke(providerId: String): Flow<SyncProgress> = flow {
        emit(SyncProgress.Starting)
        try {
            val provider = providerRepository.getProviderById(providerId) ?: throw IllegalArgumentException("Provider nicht gefunden")
            when (provider.type) {
                "xtream" -> {
                    if (provider.username == null || provider.password == null) throw IllegalArgumentException("Username und Passwort erforderlich")
                    syncXtreamCodesFull(this, providerId, provider.serverUrl, provider.username, provider.password)
                }
                "m3u_url" -> emit(SyncProgress.Error("M3U URL Sync noch nicht implementiert"))
                "m3u_local" -> emit(SyncProgress.Error("M3U Local Sync noch nicht implementiert"))
            }
            emit(SyncProgress.Success)
        } catch (e: Exception) { emit(SyncProgress.Error(e.message ?: "Unbekannter Fehler")) }
    }

    // KEY FIX: Only sync VOD categories - NO STREAMS during onboarding!
    fun syncVodCategoriesOnly(providerId: String): Flow<SyncProgress> = flow {
        emit(SyncProgress.Starting)
        try {
            val provider = providerRepository.getProviderById(providerId) ?: throw IllegalArgumentException("Provider nicht gefunden")
            if (provider.type != "xtream" || provider.username == null || provider.password == null) { emit(SyncProgress.Error("Nur Xtream Codes wird unterstutzt")); return@flow }
            val api = xtreamCodesService.createApi(provider.serverUrl)
            val apiUrl = XtreamCodesService.buildApiUrl(provider.serverUrl)
            emit(SyncProgress.Progress("Lade Film-Kategorien...", 0))
            val vodCategories = try { withTimeout(30000) { api.getVodCategories(apiUrl, provider.username, provider.password) } } catch (e: Exception) { emit(SyncProgress.Error("Fehler: ${e.message}")); return@flow }
            if (vodCategories.isEmpty()) { emit(SyncProgress.Progress("Keine Film-Kategorien gefunden", 100)); emit(SyncProgress.Success); return@flow }
            val vodCategoryEntities = vodCategories.mapNotNull { cat -> cat.categoryId?.let { CategoryEntity(id = "${providerId}_vod_$it", providerId = providerId, name = cat.categoryName ?: "Unknown", type = "vod") } }
            vodCategoryEntities.chunked(100).forEach { batch -> categoryRepository.addCategories(batch) }
            emit(SyncProgress.Progress("${vodCategories.size} Film-Kategorien geladen", 100))
            emit(SyncProgress.Success)
        } catch (e: Exception) { emit(SyncProgress.Error(e.message ?: "Fehler beim Laden der Film-Kategorien")) }
    }

    // KEY FIX: Only sync Series categories - NO SERIES DATA during onboarding!
    fun syncSeriesCategoriesOnly(providerId: String): Flow<SyncProgress> = flow {
        emit(SyncProgress.Starting)
        try {
            val provider = providerRepository.getProviderById(providerId) ?: throw IllegalArgumentException("Provider nicht gefunden")
            if (provider.type != "xtream" || provider.username == null || provider.password == null) { emit(SyncProgress.Error("Nur Xtream Codes wird unterstutzt")); return@flow }
            val api = xtreamCodesService.createApi(provider.serverUrl)
            val apiUrl = XtreamCodesService.buildApiUrl(provider.serverUrl)
            emit(SyncProgress.Progress("Lade Serien-Kategorien...", 0))
            val seriesCategories = try { withTimeout(30000) { api.getSeriesCategories(apiUrl, provider.username, provider.password) } } catch (e: Exception) { emit(SyncProgress.Error("Fehler: ${e.message}")); return@flow }
            if (seriesCategories.isEmpty()) { emit(SyncProgress.Progress("Keine Serien-Kategorien gefunden", 100)); emit(SyncProgress.Success); return@flow }
            val seriesCatEntities = seriesCategories.mapNotNull { cat -> cat.categoryId?.let { CategoryEntity(id = "${providerId}_series_$it", providerId = providerId, name = cat.categoryName ?: "Unknown", type = "series") } }
            seriesCatEntities.chunked(100).forEach { batch -> categoryRepository.addCategories(batch) }
            emit(SyncProgress.Progress("${seriesCategories.size} Serien-Kategorien geladen", 100))
            emit(SyncProgress.Success)
        } catch (e: Exception) { emit(SyncProgress.Error(e.message ?: "Fehler beim Laden der Serien-Kategorien")) }
    }

    // Sync VOD streams only (can be called later, not during onboarding)
    fun syncVodStreams(providerId: String): Flow<SyncProgress> = flow {
        emit(SyncProgress.Starting)
        try {
            val provider = providerRepository.getProviderById(providerId) ?: throw IllegalArgumentException("Provider nicht gefunden")
            if (provider.type != "xtream" || provider.username == null || provider.password == null) { emit(SyncProgress.Error("Nur Xtream Codes wird unterstutzt")); return@flow }
            val api = xtreamCodesService.createApi(provider.serverUrl)
            val apiUrl = XtreamCodesService.buildApiUrl(provider.serverUrl)
            emit(SyncProgress.Progress("Lade Filme...", 0))
            val vodStreams = try { withTimeout(120000) { api.getVodStreams(apiUrl, provider.username, provider.password) } } catch (e: Exception) { emit(SyncProgress.Error("Fehler: ${e.message}")); return@flow }
            if (vodStreams.isEmpty()) { emit(SyncProgress.Progress("Keine Filme gefunden", 100)); emit(SyncProgress.Success); return@flow }
            val batches = vodStreams.chunked(100)
            batches.forEachIndexed { index, batch ->
                val vodEntities = batch.mapNotNull { stream -> stream.streamId?.let { streamId -> StreamEntity(id = "${providerId}_vod_$streamId", categoryId = stream.categoryId?.let { "${providerId}_vod_$it" } ?: "", providerId = providerId, name = stream.name ?: "Unknown", streamUrl = XtreamCodesService.buildVodUrl(provider.serverUrl, provider.username, provider.password, streamId.toString(), stream.containerExtension ?: "mp4"), logoUrl = stream.streamIcon, epgChannelId = null, type = "vod", rating = stream.rating, rating5Based = stream.rating5Based, added = stream.added, containerExtension = stream.containerExtension) } }
                streamRepository.addStreams(vodEntities)
                val progress = ((index + 1) * 100 / batches.size).coerceIn(0, 100)
                emit(SyncProgress.Progress("Filme werden verarbeitet... ($progress%)", progress))
            }
            emit(SyncProgress.Progress("${vodStreams.size} Filme synchronisiert", 100))
            emit(SyncProgress.Success)
        } catch (e: Exception) { emit(SyncProgress.Error(e.message ?: "Fehler beim Synchronisieren der Filme")) }
    }

    // Sync Series data only (can be called later, not during onboarding)
    fun syncSeriesData(providerId: String): Flow<SyncProgress> = flow {
        emit(SyncProgress.Starting)
        try {
            val provider = providerRepository.getProviderById(providerId) ?: throw IllegalArgumentException("Provider nicht gefunden")
            if (provider.type != "xtream" || provider.username == null || provider.password == null) { emit(SyncProgress.Error("Nur Xtream Codes wird unterstutzt")); return@flow }
            val api = xtreamCodesService.createApi(provider.serverUrl)
            val apiUrl = XtreamCodesService.buildApiUrl(provider.serverUrl)
            emit(SyncProgress.Progress("Lade Serien...", 0))
            val seriesList = try { withTimeout(60000) { api.getSeries(apiUrl, provider.username, provider.password) } } catch (e: Exception) { emit(SyncProgress.Error("Fehler: ${e.message}")); return@flow }
            if (seriesList.isEmpty()) { emit(SyncProgress.Progress("Keine Serien gefunden", 100)); emit(SyncProgress.Success); return@flow }
            val batches = seriesList.chunked(50)
            batches.forEachIndexed { index, batch ->
                val seriesEntities = batch.mapNotNull { seriesDto -> seriesDto.seriesId?.toString()?.let { seriesId -> SeriesEntity(id = "${providerId}_series_$seriesId", providerId = providerId, categoryId = seriesDto.categoryId?.let { "${providerId}_series_$it" } ?: "", name = seriesDto.name ?: "Unknown", plot = seriesDto.plot, posterUrl = seriesDto.cover, backdropUrl = seriesDto.backdropPath?.firstOrNull(), rating = seriesDto.rating5Based?.toString() ?: seriesDto.rating, releaseDate = seriesDto.releaseDate, genre = seriesDto.genre, cast = seriesDto.cast, director = seriesDto.director, episodeRunTime = seriesDto.episodeRunTime, totalSeasons = 0, totalEpisodes = 0) } }
                if (seriesEntities.isNotEmpty()) seriesRepository.insertSeries(seriesEntities)
                val progress = ((index + 1) * 100 / batches.size).coerceIn(0, 100)
                emit(SyncProgress.Progress("Serien werden verarbeitet... ($progress%)", progress))
            }
            emit(SyncProgress.Progress("${seriesList.size} Serien synchronisiert", 100))
            emit(SyncProgress.Success)
        } catch (e: Exception) { emit(SyncProgress.Error(e.message ?: "Fehler beim Synchronisieren der Serien")) }
    }

    private suspend fun syncXtreamCodesQuick(collector: kotlinx.coroutines.flow.FlowCollector<SyncProgress>, providerId: String, baseUrl: String, username: String, password: String) {
        val api = xtreamCodesService.createApi(baseUrl)
        val apiUrl = XtreamCodesService.buildApiUrl(baseUrl)
        collector.emit(SyncProgress.Progress("Verbindung wird hergestellt...", 5))
        try { withTimeout(10000) { val auth = api.authenticate(apiUrl, username, password); if (auth.userInfo?.auth != 1) throw IllegalStateException("Authentifizierung fehlgeschlagen") } } catch (e: Exception) { throw IllegalStateException("Verbindung fehlgeschlagen: ${e.message}") }
        categoryRepository.deleteCategoriesByProvider(providerId)
        streamRepository.deleteStreamsByProvider(providerId)
        collector.emit(SyncProgress.Progress("Synchronisiere Live TV...", 20))
        syncLiveTV(api, apiUrl, providerId, baseUrl, username, password, collector)
        collector.emit(SyncProgress.Progress("Fertig! VOD und Serien konnen spater synchronisiert werden.", 100))
    }

    private suspend fun syncXtreamCodesFull(collector: kotlinx.coroutines.flow.FlowCollector<SyncProgress>, providerId: String, baseUrl: String, username: String, password: String) {
        val api = xtreamCodesService.createApi(baseUrl)
        val apiUrl = XtreamCodesService.buildApiUrl(baseUrl)
        collector.emit(SyncProgress.Progress("Verbindung wird hergestellt...", 5))
        try { withTimeout(10000) { val auth = api.authenticate(apiUrl, username, password); if (auth.userInfo?.auth != 1) throw IllegalStateException("Authentifizierung fehlgeschlagen") } } catch (e: Exception) { throw IllegalStateException("Verbindung fehlgeschlagen: ${e.message}") }
        categoryRepository.deleteCategoriesByProvider(providerId)
        streamRepository.deleteStreamsByProvider(providerId)
        collector.emit(SyncProgress.Progress("Synchronisiere Live TV...", 15))
        syncLiveTV(api, apiUrl, providerId, baseUrl, username, password, collector)
        collector.emit(SyncProgress.Progress("Synchronisiere Filme...", 40))
        try { syncVOD(api, apiUrl, providerId, baseUrl, username, password); collector.emit(SyncProgress.Progress("Filme synchronisiert", 65)) } catch (e: Exception) { collector.emit(SyncProgress.Progress("Film-Sync fehlgeschlagen: ${e.message}", 65)) }
        collector.emit(SyncProgress.Progress("Synchronisiere Serien...", 70))
        try { syncSeries(api, apiUrl, providerId, username, password); collector.emit(SyncProgress.Progress("Serien synchronisiert", 90)) } catch (e: Exception) { collector.emit(SyncProgress.Progress("Serien-Sync fehlgeschlagen: ${e.message}", 90)) }
        collector.emit(SyncProgress.Progress("Synchronisation abgeschlossen!", 100))
    }

    private suspend fun syncLiveTV(api: XtreamCodesApiDynamic, apiUrl: String, providerId: String, baseUrl: String, username: String, password: String, collector: kotlinx.coroutines.flow.FlowCollector<SyncProgress>) {
        collector.emit(SyncProgress.Progress("Lade Live TV Kategorien...", 25))
        val liveCategories = try { withTimeout(30000) { api.getLiveCategories(apiUrl, username, password) } } catch (e: Exception) { emptyList() }
        if (liveCategories.isNotEmpty()) {
            val categoryEntities = liveCategories.mapNotNull { cat -> cat.categoryId?.let { CategoryEntity(id = "${providerId}_live_$it", providerId = providerId, name = cat.categoryName ?: "Unknown", type = "live") } }
            categoryEntities.chunked(100).forEach { batch -> categoryRepository.addCategories(batch) }
        }
        collector.emit(SyncProgress.Progress("Lade Kanale...", 50))
        val liveStreams = try { withTimeout(60000) { api.getLiveStreams(apiUrl, username, password) } } catch (e: Exception) { emptyList() }
        if (liveStreams.isNotEmpty()) {
            val batches = liveStreams.chunked(100)
            batches.forEachIndexed { index, batch ->
                val streamEntities = batch.mapNotNull { stream -> stream.streamId?.let { streamId -> StreamEntity(id = "${providerId}_live_$streamId", categoryId = stream.categoryId?.let { "${providerId}_live_$it" } ?: "", providerId = providerId, name = stream.name ?: "Unknown", streamUrl = XtreamCodesService.buildStreamUrl(baseUrl, username, password, streamId.toString()), logoUrl = stream.streamIcon, epgChannelId = stream.epgChannelId, type = "live") } }
                streamRepository.addStreams(streamEntities)
                if (index % 5 == 0 || index == batches.size - 1) { val progress = 50 + ((index + 1) * 50 / batches.size); collector.emit(SyncProgress.Progress("${liveStreams.size} Kanäle werden verarbeitet...", progress.coerceIn(50, 100))) }
            }
        }
        collector.emit(SyncProgress.Progress("${liveStreams.size} Kanale geladen", 100))
    }

    private suspend fun syncVOD(api: XtreamCodesApiDynamic, apiUrl: String, providerId: String, baseUrl: String, username: String, password: String) {
        val vodCategories = try { withTimeout(30000) { api.getVodCategories(apiUrl, username, password) } } catch (e: Exception) { emptyList() }
        if (vodCategories.isNotEmpty()) {
            val vodCategoryEntities = vodCategories.mapNotNull { cat -> cat.categoryId?.let { CategoryEntity(id = "${providerId}_vod_$it", providerId = providerId, name = cat.categoryName ?: "Unknown", type = "vod") } }
            vodCategoryEntities.chunked(100).forEach { batch -> categoryRepository.addCategories(batch) }
        }
        val vodStreams = try { withTimeout(120000) { api.getVodStreams(apiUrl, username, password) } } catch (e: Exception) { emptyList() }
        val vodEntities = vodStreams.mapNotNull { stream -> stream.streamId?.let { streamId -> StreamEntity(id = "${providerId}_vod_$streamId", categoryId = stream.categoryId?.let { "${providerId}_vod_$it" } ?: "", providerId = providerId, name = stream.name ?: "Unknown", streamUrl = XtreamCodesService.buildVodUrl(baseUrl, username, password, streamId.toString(), stream.containerExtension ?: "mp4"), logoUrl = stream.streamIcon, epgChannelId = null, type = "vod", rating = stream.rating, rating5Based = stream.rating5Based, added = stream.added, containerExtension = stream.containerExtension) } }
        streamRepository.addStreams(vodEntities)
    }

    private suspend fun syncSeries(api: XtreamCodesApiDynamic, apiUrl: String, providerId: String, username: String, password: String) {
        val seriesCategories = try { withTimeout(30000) { api.getSeriesCategories(apiUrl, username, password) } } catch (e: Exception) { emptyList() }
        if (seriesCategories.isNotEmpty()) {
            val seriesCatEntities = seriesCategories.mapNotNull { cat -> cat.categoryId?.let { CategoryEntity(id = "${providerId}_series_$it", providerId = providerId, name = cat.categoryName ?: "Unknown", type = "series") } }
            seriesCatEntities.chunked(100).forEach { batch -> categoryRepository.addCategories(batch) }
        }
        val seriesList = try { withTimeout(60000) { api.getSeries(apiUrl, username, password) } } catch (e: Exception) { emptyList() }
        val seriesEntities = seriesList.mapNotNull { seriesDto -> seriesDto.seriesId?.toString()?.let { seriesId -> SeriesEntity(id = "${providerId}_series_$seriesId", providerId = providerId, categoryId = seriesDto.categoryId?.let { "${providerId}_series_$it" } ?: "", name = seriesDto.name ?: "Unknown", plot = seriesDto.plot, posterUrl = seriesDto.cover, backdropUrl = seriesDto.backdropPath?.firstOrNull(), rating = seriesDto.rating5Based?.toString() ?: seriesDto.rating, releaseDate = seriesDto.releaseDate, genre = seriesDto.genre, cast = seriesDto.cast, director = seriesDto.director, episodeRunTime = seriesDto.episodeRunTime, totalSeasons = 0, totalEpisodes = 0) } }
        if (seriesEntities.isNotEmpty()) seriesRepository.insertSeries(seriesEntities)
    }
}
