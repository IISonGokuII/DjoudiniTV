package com.nextgen.iptv.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nextgen.iptv.data.local.entity.EpgEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpgEventDao {
    @Query("SELECT * FROM epg_events WHERE channelId = :channelId ORDER BY startTime ASC")
    fun getByChannelId(channelId: String): Flow<List<EpgEventEntity>>
    
    @Query("SELECT * FROM epg_events WHERE channelId = :channelId AND startTime <= :currentTime AND endTime >= :currentTime LIMIT 1")
    suspend fun getCurrentEvent(channelId: String, currentTime: Long): EpgEventEntity?
    
    @Query("SELECT * FROM epg_events WHERE channelId = :channelId AND startTime > :currentTime ORDER BY startTime ASC LIMIT :limit")
    suspend fun getUpcomingEvents(channelId: String, currentTime: Long, limit: Int): List<EpgEventEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: EpgEventEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<EpgEventEntity>)
    
    // Alias for insertAll used by some components
    suspend fun addEvents(events: List<EpgEventEntity>) = insertAll(events)
    
    @Update
    suspend fun update(event: EpgEventEntity)
    
    @Delete
    suspend fun delete(event: EpgEventEntity)
    
    @Query("DELETE FROM epg_events WHERE channelId = :channelId")
    suspend fun deleteByChannelId(channelId: String)
    
    @Query("DELETE FROM epg_events WHERE endTime < :timestamp")
    suspend fun deleteOldEvents(timestamp: Long)
    
    @Query("DELETE FROM epg_events")
    suspend fun deleteAll()
}
