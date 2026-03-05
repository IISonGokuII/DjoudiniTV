package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.data.local.entity.ProviderEntity
import kotlinx.coroutines.flow.Flow

interface ProviderRepository {
    fun getAllProviders(): Flow<List<ProviderEntity>>
    suspend fun getProviderById(id: String): ProviderEntity?
    suspend fun addProvider(provider: ProviderEntity)
    suspend fun updateProvider(provider: ProviderEntity)
    suspend fun deleteProvider(provider: ProviderEntity)
    suspend fun deleteProviderById(id: String)
    suspend fun getProviderCount(): Int
}
