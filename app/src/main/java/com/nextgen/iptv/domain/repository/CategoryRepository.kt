package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<CategoryEntity>>
    fun getCategoriesByProvider(providerId: String): Flow<List<CategoryEntity>>
    fun getCategoriesByType(type: String): Flow<List<CategoryEntity>>
    fun getCategoriesByProviderAndType(providerId: String, type: String): Flow<List<CategoryEntity>>
    suspend fun getCategoryById(id: String): CategoryEntity?
    suspend fun insertCategory(category: CategoryEntity)
    suspend fun addCategories(categories: List<CategoryEntity>)
    suspend fun updateCategory(category: CategoryEntity)
    suspend fun deleteCategory(id: String)
    suspend fun deleteCategoriesByProvider(providerId: String)
}
