package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.data.local.entity.EpisodeEntity
import com.nextgen.iptv.data.local.entity.SeasonEntity
import com.nextgen.iptv.data.local.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    // Series
    suspend fun insertSeries(series: List<SeriesEntity>)
    suspend fun getSeriesById(seriesId: String): SeriesEntity?
    fun getAllSeries(): Flow<List<SeriesEntity>>
    fun getSeriesByCategory(categoryId: String): Flow<List<SeriesEntity>>
    fun getSeriesByProvider(providerId: String): Flow<List<SeriesEntity>>
    fun searchSeries(query: String): Flow<List<SeriesEntity>>
    suspend fun deleteSeriesByProvider(providerId: String)
    
    // Seasons
    suspend fun insertSeasons(seasons: List<SeasonEntity>)
    fun getSeasonsBySeries(seriesId: String): Flow<List<SeasonEntity>>
    suspend fun getSeasonById(seasonId: String): SeasonEntity?
    suspend fun deleteSeasonsByProvider(providerId: String)
    
    // Episodes
    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)
    fun getEpisodesBySeries(seriesId: String): Flow<List<EpisodeEntity>>
    fun getEpisodesBySeason(seasonId: String): Flow<List<EpisodeEntity>>
    suspend fun getEpisodeById(episodeId: String): EpisodeEntity?
    suspend fun deleteEpisodesByProvider(providerId: String)
}
