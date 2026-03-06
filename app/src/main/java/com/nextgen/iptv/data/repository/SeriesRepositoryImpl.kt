package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.SeriesDao
import com.nextgen.iptv.data.local.entity.EpisodeEntity
import com.nextgen.iptv.data.local.entity.SeasonEntity
import com.nextgen.iptv.data.local.entity.SeriesEntity
import com.nextgen.iptv.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeriesRepositoryImpl @Inject constructor(
    private val seriesDao: SeriesDao
) : SeriesRepository {
    
    // Series
    override suspend fun insertSeries(series: List<SeriesEntity>) = seriesDao.insertSeries(series)
    override suspend fun getSeriesById(seriesId: String): SeriesEntity? = seriesDao.getSeriesById(seriesId)
    override fun getAllSeries(): Flow<List<SeriesEntity>> = seriesDao.getAllSeries()
    override fun getSeriesByCategory(categoryId: String): Flow<List<SeriesEntity>> = seriesDao.getSeriesByCategory(categoryId)
    override fun getSeriesByProvider(providerId: String): Flow<List<SeriesEntity>> = seriesDao.getSeriesByProvider(providerId)
    override fun searchSeries(query: String): Flow<List<SeriesEntity>> = seriesDao.searchSeries(query)
    override suspend fun deleteSeriesByProvider(providerId: String) = seriesDao.deleteSeriesByProvider(providerId)
    
    // Seasons
    override suspend fun insertSeasons(seasons: List<SeasonEntity>) = seriesDao.insertSeasons(seasons)
    override fun getSeasonsBySeries(seriesId: String): Flow<List<SeasonEntity>> = seriesDao.getSeasonsBySeries(seriesId)
    override suspend fun getSeasonById(seasonId: String): SeasonEntity? = seriesDao.getSeasonById(seasonId)
    override suspend fun deleteSeasonsByProvider(providerId: String) = seriesDao.deleteSeasonsByProvider(providerId)
    
    // Episodes
    override suspend fun insertEpisodes(episodes: List<EpisodeEntity>) = seriesDao.insertEpisodes(episodes)
    override fun getEpisodesBySeries(seriesId: String): Flow<List<EpisodeEntity>> = seriesDao.getEpisodesBySeries(seriesId)
    override fun getEpisodesBySeason(seasonId: String): Flow<List<EpisodeEntity>> = seriesDao.getEpisodesBySeason(seasonId)
    override suspend fun getEpisodeById(episodeId: String): EpisodeEntity? = seriesDao.getEpisodeById(episodeId)
    override suspend fun deleteEpisodesByProvider(providerId: String) = seriesDao.deleteEpisodesByProvider(providerId)
}
