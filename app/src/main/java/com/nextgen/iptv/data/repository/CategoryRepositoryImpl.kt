package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.CategoryDao
import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    
    override fun getAllCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategories()
    }
    
    override fun getCategoriesByProvider(providerId: String): Flow<List<CategoryEntity>> {
        return categoryDao.getCategoriesByProvider(providerId)
    }
    
    override fun getCategoriesByType(type: String): Flow<List<CategoryEntity>> {
        return categoryDao.getCategoriesByType(type)
    }
    
    override fun getCategoriesByProviderAndType(providerId: String, type: String): Flow<List<CategoryEntity>> {
        return categoryDao.getCategoriesByProviderAndType(providerId, type)
    }
    
    override suspend fun getCategoryById(id: String): CategoryEntity? {
        return categoryDao.getCategoryById(id)
    }
    
    override suspend fun insertCategory(category: CategoryEntity) {
        categoryDao.insertCategory(category)
    }
    
    override suspend fun addCategories(categories: List<CategoryEntity>) {
        categoryDao.insertCategories(categories)
    }
    
    override suspend fun updateCategory(category: CategoryEntity) {
        categoryDao.updateCategory(category)
    }
    
    override suspend fun deleteCategory(id: String) {
        categoryDao.deleteCategoryById(id)
    }
    
    override suspend fun deleteCategoriesByProvider(providerId: String) {
        categoryDao.deleteCategoriesByProvider(providerId)
    }
}
