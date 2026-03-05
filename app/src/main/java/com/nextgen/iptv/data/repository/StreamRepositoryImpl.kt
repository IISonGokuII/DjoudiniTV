package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.StreamDao
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.domain.repository.StreamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StreamRepositoryImpl @Inject constructor(
    private val streamDao: StreamDao
) : StreamRepository {
    
    override fun getAllStreams(): Flow<List<StreamEntity>> {
        return streamDao.getAllStreams()
    }
    
    override fun getStreamsByProvider(providerId: String): Flow<List<StreamEntity>> {
        return streamDao.getStreamsByProvider(providerId)
    }
    
    override fun getStreamsByCategory(categoryId: String): Flow<List<StreamEntity>> {
        return streamDao.getStreamsByCategory(categoryId)
    }
    
    override fun getStreamsByType(type: String): Flow<List<StreamEntity>> {
        return streamDao.getStreamsByType(type)
    }
    
    override fun searchStreams(query: String): Flow<List<StreamEntity>> {
        return streamDao.searchStreams(query)
    }
    
    override fun getFavoriteStreams(): Flow<List<StreamEntity>> {
        return streamDao.getFavoriteStreams()
    }
    
    override suspend fun getStreamById(id: String): StreamEntity? {
        return streamDao.getStreamById(id)
    }
    
    override suspend fun insertStream(stream: StreamEntity) {
        streamDao.insertStream(stream)
    }
    
    override suspend fun addStreams(streams: List<StreamEntity>) {
        streamDao.insertStreams(streams)
    }
    
    override suspend fun updateStream(stream: StreamEntity) {
        streamDao.updateStream(stream)
    }
    
    override suspend fun deleteStream(id: String) {
        streamDao.deleteStreamById(id)
    }
    
    override suspend fun deleteStreamsByProvider(providerId: String) {
        streamDao.deleteStreamsByProvider(providerId)
    }
    
    override suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        streamDao.updateFavoriteStatus(id, isFavorite)
    }
}
