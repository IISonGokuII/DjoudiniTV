package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.data.local.entity.ProviderEntity
import kotlinx.coroutines.flow.Flow

interface ProviderRepository {
    fun getAllProviders(): Flow<List<ProviderEntity>>
    suspend fun getProviderById(id: String): ProviderEntity?
    suspend fun insertProvider(provider: ProviderEntity)
    suspend fun updateProvider(provider: ProviderEntity)
    suspend fun deleteProvider(id: String)
}
