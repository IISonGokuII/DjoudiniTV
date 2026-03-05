package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.CategoryDao
import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    
    override fun getAllCategories(): Flow<List<CategoryEntity>> = categoryDao.getAll()
    
    override fun getCategoriesByProvider(providerId: String): Flow<List<CategoryEntity>> = 
        categoryDao.getByProviderId(providerId)
    
    override fun getCategoriesByProviderAndType(providerId: String, type: String): Flow<List<CategoryEntity>> = 
        categoryDao.getByProviderAndType(providerId, type)
    
    override suspend fun getCategoryById(id: String): CategoryEntity? = categoryDao.getById(id)
    
    override suspend fun addCategory(category: CategoryEntity) = categoryDao.insert(category)
    
    override suspend fun addCategories(categories: List<CategoryEntity>) = 
        categoryDao.insertAll(categories)
    
    override suspend fun updateCategory(category: CategoryEntity) = categoryDao.update(category)
    
    override suspend fun deleteCategory(category: CategoryEntity) = categoryDao.delete(category)
    
    override suspend fun deleteCategoriesByProvider(providerId: String) = 
        categoryDao.deleteByProviderId(providerId)
    
    override suspend fun deleteAllCategories() = categoryDao.deleteAll()
}
