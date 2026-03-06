package com.nextgen.iptv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nextgen.iptv.data.local.entity.EpisodeEntity
import com.nextgen.iptv.data.local.entity.SeasonEntity
import com.nextgen.iptv.data.local.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {
    
    // Series
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: List<SeriesEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: SeriesEntity)
    
    @Query("SELECT * FROM series WHERE id = :seriesId")
    suspend fun getSeriesById(seriesId: String): SeriesEntity?
    
    @Query("SELECT * FROM series ORDER BY name ASC")
    fun getAllSeries(): Flow<List<SeriesEntity>>
    
    @Query("SELECT * FROM series WHERE categoryId = :categoryId")
    fun getSeriesByCategory(categoryId: String): Flow<List<SeriesEntity>>
    
    @Query("SELECT * FROM series WHERE categoryId IN (:categoryIds)")
    fun getSeriesByCategoryIds(categoryIds: List<String>): Flow<List<SeriesEntity>>
    
    @Query("SELECT * FROM series WHERE providerId = :providerId")
    fun getSeriesByProvider(providerId: String): Flow<List<SeriesEntity>>
    
    @Query("SELECT * FROM series WHERE name LIKE '%' || :query || '%'")
    fun searchSeries(query: String): Flow<List<SeriesEntity>>
    
    @Query("DELETE FROM series WHERE providerId = :providerId")
    suspend fun deleteSeriesByProvider(providerId: String)
    
    // Seasons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: List<SeasonEntity>)
    
    @Query("SELECT * FROM seasons WHERE seriesId = :seriesId ORDER BY seasonNumber")
    fun getSeasonsBySeries(seriesId: String): Flow<List<SeasonEntity>>
    
    @Query("SELECT * FROM seasons WHERE id = :seasonId")
    suspend fun getSeasonById(seasonId: String): SeasonEntity?
    
    @Query("DELETE FROM seasons WHERE seriesId IN (SELECT id FROM series WHERE providerId = :providerId)")
    suspend fun deleteSeasonsByProvider(providerId: String)
    
    // Episodes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)
    
    @Query("SELECT * FROM episodes WHERE seriesId = :seriesId ORDER BY seasonNumber, episodeNumber")
    fun getEpisodesBySeries(seriesId: String): Flow<List<EpisodeEntity>>
    
    @Query("SELECT * FROM episodes WHERE seasonId = :seasonId ORDER BY episodeNumber")
    fun getEpisodesBySeason(seasonId: String): Flow<List<EpisodeEntity>>
    
    @Query("SELECT * FROM episodes WHERE id = :episodeId")
    suspend fun getEpisodeById(episodeId: String): EpisodeEntity?
    
    @Query("DELETE FROM episodes WHERE seriesId IN (SELECT id FROM series WHERE providerId = :providerId)")
    suspend fun deleteEpisodesByProvider(providerId: String)
}
