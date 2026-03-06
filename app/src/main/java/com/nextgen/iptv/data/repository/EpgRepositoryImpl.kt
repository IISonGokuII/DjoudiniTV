package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.EpgEventDao
import com.nextgen.iptv.data.local.entity.EpgEventEntity
import com.nextgen.iptv.domain.repository.EpgRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpgRepositoryImpl @Inject constructor(
    private val epgDao: EpgEventDao
) : EpgRepository {
    
    override fun getEpgForChannel(channelId: String): Flow<List<EpgEventEntity>> {
        return epgDao.getByChannelId(channelId)
    }
    
    override suspend fun getCurrentEvent(channelId: String, currentTime: Long): EpgEventEntity? {
        return epgDao.getCurrentEvent(channelId, currentTime)
    }
    
    override suspend fun getUpcomingEvents(channelId: String, currentTime: Long, limit: Int): List<EpgEventEntity> {
        return epgDao.getUpcomingEvents(channelId, currentTime, limit)
    }
    
    override suspend fun insertEpg(epg: EpgEventEntity) {
        epgDao.insert(epg)
    }
    
    override suspend fun insertEpgList(epgList: List<EpgEventEntity>) {
        epgDao.insertAll(epgList)
    }
    
    override suspend fun addEvents(events: List<EpgEventEntity>) {
        epgDao.insertAll(events)
    }
    
    override suspend fun deleteEpgForProvider(providerId: String) {
        // TODO: Implement provider-based deletion
    }
    
    override suspend fun deleteOldEpg(currentTime: Long) {
        epgDao.deleteOldEvents(currentTime)
    }
    
    override suspend fun deleteOldEvents(timestamp: Long) {
        epgDao.deleteOldEvents(timestamp)
    }
}
