package com.nextgen.iptv.domain.repository

import androidx.paging.PagingSource
import com.nextgen.iptv.data.local.entity.StreamEntity
import kotlinx.coroutines.flow.Flow

interface StreamRepository {
    fun getAllStreams(): Flow<List<StreamEntity>>
    fun getStreamsByCategory(categoryId: String): Flow<List<StreamEntity>>
    fun getStreamsByType(type: String): Flow<List<StreamEntity>>
    fun getStreamsByProvider(providerId: String): Flow<List<StreamEntity>>
    suspend fun getStreamById(id: String): StreamEntity?
    fun getStreamsByCategoryPaged(categoryId: String): PagingSource<Int, StreamEntity>
    fun getStreamsByTypePaged(type: String): PagingSource<Int, StreamEntity>
    suspend fun addStream(stream: StreamEntity)
    suspend fun addStreams(streams: List<StreamEntity>)
    suspend fun updateStream(stream: StreamEntity)
    suspend fun deleteStream(stream: StreamEntity)
    suspend fun deleteStreamsByProvider(providerId: String)
    suspend fun deleteAllStreams()
    suspend fun getStreamCountByProvider(providerId: String): Int
}
