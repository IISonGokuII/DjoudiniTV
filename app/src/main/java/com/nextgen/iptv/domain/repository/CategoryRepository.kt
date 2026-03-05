package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<CategoryEntity>>
    fun getCategoriesByProvider(providerId: String): Flow<List<CategoryEntity>>
    fun getCategoriesByProviderAndType(providerId: String, type: String): Flow<List<CategoryEntity>>
    suspend fun getCategoryById(id: String): CategoryEntity?
    suspend fun addCategory(category: CategoryEntity)
    suspend fun addCategories(categories: List<CategoryEntity>)
    suspend fun updateCategory(category: CategoryEntity)
    suspend fun deleteCategory(category: CategoryEntity)
    suspend fun deleteCategoriesByProvider(providerId: String)
    suspend fun deleteAllCategories()
}
