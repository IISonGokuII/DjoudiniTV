package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.ProviderDao
import com.nextgen.iptv.data.local.entity.ProviderEntity
import com.nextgen.iptv.domain.repository.ProviderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProviderRepositoryImpl @Inject constructor(
    private val providerDao: ProviderDao
) : ProviderRepository {
    
    override fun getAllProviders(): Flow<List<ProviderEntity>> = providerDao.getAll()
    
    override suspend fun getProviderById(id: String): ProviderEntity? = providerDao.getById(id)
    
    override suspend fun addProvider(provider: ProviderEntity) = providerDao.insert(provider)
    
    override suspend fun updateProvider(provider: ProviderEntity) = providerDao.update(provider)
    
    override suspend fun deleteProvider(provider: ProviderEntity) = providerDao.delete(provider)
    
    override suspend fun deleteProviderById(id: String) = providerDao.deleteById(id)
    
    override suspend fun getProviderCount(): Int = providerDao.getCount()
}
