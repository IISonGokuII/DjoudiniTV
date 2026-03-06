package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.StreamDao
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.domain.repository.StreamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StreamRepositoryImpl @Inject constructor(
    private val streamDao: StreamDao
) : StreamRepository {
    
    override fun getAllStreams(): Flow<List<StreamEntity>> {
        return streamDao.getAll()
    }
    
    override fun getStreamsByProvider(providerId: String): Flow<List<StreamEntity>> {
        return streamDao.getByProviderId(providerId)
    }
    
    override fun getStreamsByCategory(categoryId: String): Flow<List<StreamEntity>> {
        return streamDao.getByCategoryId(categoryId)
    }
    
    override fun getStreamsByType(type: String): Flow<List<StreamEntity>> {
        return streamDao.getByType(type)
    }
    
    override fun searchStreams(query: String): Flow<List<StreamEntity>> {
        // Simple search - filter by name containing query
        return streamDao.getAll().map { streams ->
            streams.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
    
    override fun getFavoriteStreams(): Flow<List<StreamEntity>> {
        // Favorites not implemented in DAO yet
        return streamDao.getAll()
    }
    
    override suspend fun getStreamById(id: String): StreamEntity? {
        return streamDao.getById(id)
    }
    
    override suspend fun insertStream(stream: StreamEntity) {
        streamDao.insert(stream)
    }
    
    override suspend fun addStreams(streams: List<StreamEntity>) {
        streamDao.insertAll(streams)
    }
    
    override suspend fun updateStream(stream: StreamEntity) {
        streamDao.update(stream)
    }
    
    override suspend fun deleteStream(id: String) {
        val stream = streamDao.getById(id)
        stream?.let { streamDao.delete(it) }
    }
    
    override suspend fun deleteStreamsByProvider(providerId: String) {
        streamDao.deleteByProviderId(providerId)
    }
    
    override suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        // Not implemented in DAO yet
    }
}
