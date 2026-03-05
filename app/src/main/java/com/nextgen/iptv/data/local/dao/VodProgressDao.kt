package com.nextgen.iptv.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nextgen.iptv.data.local.entity.VodProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VodProgressDao {
    @Query("SELECT * FROM vod_progress ORDER BY lastWatched DESC")
    fun getAll(): Flow<List<VodProgressEntity>>
    
    @Query("SELECT * FROM vod_progress WHERE streamId = :streamId")
    suspend fun getByStreamId(streamId: String): VodProgressEntity?
    
    @Query("SELECT progressMs FROM vod_progress WHERE streamId = :streamId")
    suspend fun getProgress(streamId: String): Long?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: VodProgressEntity)
    
    @Update
    suspend fun update(progress: VodProgressEntity)
    
    @Delete
    suspend fun delete(progress: VodProgressEntity)
    
    @Query("DELETE FROM vod_progress WHERE streamId = :streamId")
    suspend fun deleteByStreamId(streamId: String)
    
    @Query("DELETE FROM vod_progress")
    suspend fun deleteAll()
}
