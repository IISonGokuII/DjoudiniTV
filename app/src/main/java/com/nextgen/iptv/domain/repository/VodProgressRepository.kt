package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.data.local.entity.VodProgressEntity
import kotlinx.coroutines.flow.Flow

interface VodProgressRepository {
    fun getAllProgress(): Flow<List<VodProgressEntity>>
    suspend fun getProgressByStreamId(streamId: String): VodProgressEntity?
    suspend fun getProgressMs(streamId: String): Long?
    suspend fun saveProgress(progress: VodProgressEntity)
    suspend fun updateProgress(progress: VodProgressEntity)
    suspend fun deleteProgress(progress: VodProgressEntity)
    suspend fun deleteProgressByStreamId(streamId: String)
    suspend fun deleteAllProgress()
}
