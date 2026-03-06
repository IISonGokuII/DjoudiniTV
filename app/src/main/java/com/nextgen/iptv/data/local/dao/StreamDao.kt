package com.nextgen.iptv.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nextgen.iptv.data.local.entity.StreamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StreamDao {
    @Query("SELECT * FROM streams ORDER BY name ASC")
    fun getAll(): Flow<List<StreamEntity>>
    
    @Query("SELECT * FROM streams WHERE categoryId = :categoryId ORDER BY name ASC")
    fun getByCategoryId(categoryId: String): Flow<List<StreamEntity>>
    
    @Query("SELECT * FROM streams WHERE categoryId IN (:categoryIds) ORDER BY name ASC")
    fun getByCategoryIds(categoryIds: List<String>): Flow<List<StreamEntity>>
    
    @Query("SELECT * FROM streams WHERE providerId = :providerId ORDER BY name ASC")
    fun getByProviderId(providerId: String): Flow<List<StreamEntity>>
    
    @Query("SELECT * FROM streams WHERE type = :type ORDER BY name ASC")
    fun getByType(type: String): Flow<List<StreamEntity>>
    
    @Query("SELECT * FROM streams WHERE type = :type AND categoryId IN (:categoryIds) ORDER BY name ASC")
    fun getByTypeAndCategories(type: String, categoryIds: List<String>): Flow<List<StreamEntity>>
    
    @Query("SELECT * FROM streams WHERE id = :id")
    suspend fun getById(id: String): StreamEntity?
    
    // Paging support
    @Query("SELECT * FROM streams WHERE categoryId = :categoryId ORDER BY name ASC")
    fun getByCategoryPaged(categoryId: String): PagingSource<Int, StreamEntity>
    
    @Query("SELECT * FROM streams WHERE type = :type ORDER BY name ASC")
    fun getByTypePaged(type: String): PagingSource<Int, StreamEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stream: StreamEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(streams: List<StreamEntity>)
    
    @Update
    suspend fun update(stream: StreamEntity)
    
    @Delete
    suspend fun delete(stream: StreamEntity)
    
    @Query("DELETE FROM streams WHERE providerId = :providerId")
    suspend fun deleteByProviderId(providerId: String)
    
    @Query("DELETE FROM streams WHERE categoryId = :categoryId")
    suspend fun deleteByCategoryId(categoryId: String)
    
    @Query("DELETE FROM streams")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM streams WHERE providerId = :providerId")
    suspend fun getCountByProvider(providerId: String): Int
}
