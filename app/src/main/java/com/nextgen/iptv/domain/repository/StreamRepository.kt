package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.data.local.entity.StreamEntity
import kotlinx.coroutines.flow.Flow

interface StreamRepository {
    fun getAllStreams(): Flow<List<StreamEntity>>
    fun getStreamsByProvider(providerId: String): Flow<List<StreamEntity>>
    fun getStreamsByCategory(categoryId: String): Flow<List<StreamEntity>>
    fun getStreamsByCategories(categoryIds: List<String>): Flow<List<StreamEntity>>
    fun getStreamsByType(type: String): Flow<List<StreamEntity>>
    fun getStreamsByTypeAndCategories(type: String, categoryIds: List<String>): Flow<List<StreamEntity>>
    fun searchStreams(query: String): Flow<List<StreamEntity>>
    fun getFavoriteStreams(): Flow<List<StreamEntity>>
    suspend fun getStreamById(id: String): StreamEntity?
    suspend fun insertStream(stream: StreamEntity)
    suspend fun addStreams(streams: List<StreamEntity>)
    suspend fun updateStream(stream: StreamEntity)
    suspend fun deleteStream(id: String)
    suspend fun deleteStreamsByProvider(providerId: String)
    suspend fun toggleFavorite(id: String, isFavorite: Boolean)
}
