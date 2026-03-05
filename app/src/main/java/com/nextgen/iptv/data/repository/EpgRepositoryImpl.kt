package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.EpgDao
import com.nextgen.iptv.data.local.entity.EpgEntity
import com.nextgen.iptv.domain.repository.EpgRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpgRepositoryImpl @Inject constructor(
    private val epgDao: EpgDao
) : EpgRepository {
    
    override fun getEpgForChannel(channelId: String): Flow<List<EpgEntity>> {
        return epgDao.getEpgForChannel(channelId)
    }
    
    override fun getCurrentEpgForChannel(channelId: String, currentTime: Long): Flow<EpgEntity?> {
        return epgDao.getCurrentEpgForChannel(channelId, currentTime)
    }
    
    override suspend fun insertEpg(epg: EpgEntity) {
        epgDao.insertEpg(epg)
    }
    
    override suspend fun insertEpgList(epgList: List<EpgEntity>) {
        epgDao.insertEpgList(epgList)
    }
    
    override suspend fun deleteEpgForProvider(providerId: String) {
        epgDao.deleteEpgForProvider(providerId)
    }
    
    override suspend fun deleteOldEpg(currentTime: Long) {
        epgDao.deleteOldEpg(currentTime)
    }
}
