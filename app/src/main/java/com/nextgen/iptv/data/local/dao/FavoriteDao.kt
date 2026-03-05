package com.nextgen.iptv.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nextgen.iptv.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites ORDER BY addedAt DESC")
    fun getAll(): Flow<List<FavoriteEntity>>
    
    @Query("SELECT * FROM favorites WHERE streamType = :type ORDER BY addedAt DESC")
    fun getByType(type: String): Flow<List<FavoriteEntity>>
    
    @Query("SELECT * FROM favorites WHERE streamId = :streamId")
    suspend fun getByStreamId(streamId: String): FavoriteEntity?
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE streamId = :streamId)")
    suspend fun isFavorite(streamId: String): Boolean
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)
    
    @Delete
    suspend fun delete(favorite: FavoriteEntity)
    
    @Query("DELETE FROM favorites WHERE streamId = :streamId")
    suspend fun deleteByStreamId(streamId: String)
    
    @Query("DELETE FROM favorites")
    suspend fun deleteAll()
}