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
        return providerDao.getAll()
    }
    
    override suspend fun getProviderById(id: String): ProviderEntity? {
        return providerDao.getById(id)
    }
    
    override suspend fun insertProvider(provider: ProviderEntity) {
        providerDao.insert(provider)
    }
    
    override suspend fun updateProvider(provider: ProviderEntity) {
        providerDao.update(provider)
    }
    
    override suspend fun deleteProvider(id: String) {
        providerDao.deleteById(id)
    }
}
