package com.nextgen.iptv.data.repository

import androidx.paging.PagingSource
import com.nextgen.iptv.data.local.dao.StreamDao
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.domain.repository.StreamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StreamRepositoryImpl @Inject constructor(
    private val streamDao: StreamDao
) : StreamRepository {
    
    override fun getAllStreams(): Flow<List<StreamEntity>> = streamDao.getAll()
    
    override fun getStreamsByCategory(categoryId: String): Flow<List<StreamEntity>> = 
        streamDao.getByCategoryId(categoryId)
    
    override fun getStreamsByType(type: String): Flow<List<StreamEntity>> = 
        streamDao.getByType(type)
    
    override fun getStreamsByProvider(providerId: String): Flow<List<StreamEntity>> = 
        streamDao.getByProviderId(providerId)
    
    override suspend fun getStreamById(id: String): StreamEntity? = streamDao.getById(id)
    
    override fun getStreamsByCategoryPaged(categoryId: String): PagingSource<Int, StreamEntity> = 
        streamDao.getByCategoryPaged(categoryId)
    
    override fun getStreamsByTypePaged(type: String): PagingSource<Int, StreamEntity> = 
        streamDao.getByTypePaged(type)
    
    override suspend fun addStream(stream: StreamEntity) = streamDao.insert(stream)
    
    override suspend fun addStreams(streams: List<StreamEntity>) = streamDao.insertAll(streams)
    
    override suspend fun updateStream(stream: StreamEntity) = streamDao.update(stream)
    
    override suspend fun deleteStream(stream: StreamEntity) = streamDao.delete(stream)
    
    override suspend fun deleteStreamsByProvider(providerId: String) = 
        streamDao.deleteByProviderId(providerId)
    
    override suspend fun deleteAllStreams() = streamDao.deleteAll()
    
    override suspend fun getStreamCountByProvider(providerId: String): Int = 
        streamDao.getCountByProvider(providerId)
}
