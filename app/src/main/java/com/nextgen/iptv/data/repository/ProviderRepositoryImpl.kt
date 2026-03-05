package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.ProviderDao
import com.nextgen.iptv.data.local.entity.ProviderEntity
import com.nextgen.iptv.domain.repository.ProviderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProviderRepositoryImpl @Inject constructor(
    private val providerDao: ProviderDao
) : ProviderRepository {
    
    override fun getAllProviders(): Flow<List<ProviderEntity>> {
        return providerDao.getAllProviders()
    }
    
    override suspend fun getProviderById(id: String): ProviderEntity? {
        return providerDao.getProviderById(id)
    }
    
    override suspend fun insertProvider(provider: ProviderEntity) {
        providerDao.insertProvider(provider)
    }
    
    override suspend fun updateProvider(provider: ProviderEntity) {
        providerDao.updateProvider(provider)
    }
    
    override suspend fun deleteProvider(id: String) {
        providerDao.deleteProviderById(id)
    }
}
