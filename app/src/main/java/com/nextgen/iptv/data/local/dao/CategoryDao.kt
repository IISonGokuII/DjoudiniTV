package com.nextgen.iptv.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nextgen.iptv.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAll(): Flow<List<CategoryEntity>>
    
    @Query("SELECT * FROM categories WHERE providerId = :providerId ORDER BY name ASC")
    fun getByProviderId(providerId: String): Flow<List<CategoryEntity>>
    
    @Query("SELECT * FROM categories WHERE providerId = :providerId AND type = :type ORDER BY name ASC")
    fun getByProviderAndType(providerId: String, type: String): Flow<List<CategoryEntity>>
    
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getById(id: String): CategoryEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)
    
    @Update
    suspend fun update(category: CategoryEntity)
    
    @Delete
    suspend fun delete(category: CategoryEntity)
    
    @Query("DELETE FROM categories WHERE providerId = :providerId")
    suspend fun deleteByProviderId(providerId: String)
    
    @Query("DELETE FROM categories")
    suspend fun deleteAll()
}
