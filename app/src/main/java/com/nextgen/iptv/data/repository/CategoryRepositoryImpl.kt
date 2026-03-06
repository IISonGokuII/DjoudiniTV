package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.CategoryDao
import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    
    override fun getAllCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getAll()
    }
    
    override fun getCategoriesByProvider(providerId: String): Flow<List<CategoryEntity>> {
        return categoryDao.getByProviderId(providerId)
    }
    
    override fun getCategoriesByType(type: String): Flow<List<CategoryEntity>> {
        return categoryDao.getAll().map { categories ->
            categories.filter { it.type == type }
        }
    }
    
    override fun getCategoriesByProviderAndType(providerId: String, type: String): Flow<List<CategoryEntity>> {
        return categoryDao.getByProviderAndType(providerId, type)
    }
    
    override suspend fun getCategoryById(id: String): CategoryEntity? {
        return categoryDao.getById(id)
    }
    
    override suspend fun insertCategory(category: CategoryEntity) {
        categoryDao.insert(category)
    }
    
    override suspend fun addCategories(categories: List<CategoryEntity>) {
        categoryDao.insertAll(categories)
    }
    
    override suspend fun updateCategory(category: CategoryEntity) {
        categoryDao.update(category)
    }
    
    override suspend fun deleteCategory(id: String) {
        val category = categoryDao.getById(id)
        category?.let { categoryDao.delete(it) }
    }
    
    override suspend fun deleteCategoriesByProvider(providerId: String) {
        categoryDao.deleteByProviderId(providerId)
    }
}
