package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.VodProgressDao
import com.nextgen.iptv.data.local.entity.VodProgressEntity
import com.nextgen.iptv.domain.repository.VodProgressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VodProgressRepositoryImpl @Inject constructor(
    private val vodProgressDao: VodProgressDao
) : VodProgressRepository {
    
    override fun getAllProgress(): Flow<List<VodProgressEntity>> = vodProgressDao.getAll()
    
    override suspend fun getProgressByStreamId(streamId: String): VodProgressEntity? = 
        vodProgressDao.getByStreamId(streamId)
    
    override suspend fun getProgressMs(streamId: String): Long? = vodProgressDao.getProgress(streamId)
    
    override suspend fun saveProgress(progress: VodProgressEntity) = vodProgressDao.insert(progress)
    
    override suspend fun updateProgress(progress: VodProgressEntity) = vodProgressDao.update(progress)
    
    override suspend fun deleteProgress(progress: VodProgressEntity) = vodProgressDao.delete(progress)
    
    override suspend fun deleteProgressByStreamId(streamId: String) = 
        vodProgressDao.deleteByStreamId(streamId)
    
    override suspend fun deleteAllProgress() = vodProgressDao.deleteAll()
}
