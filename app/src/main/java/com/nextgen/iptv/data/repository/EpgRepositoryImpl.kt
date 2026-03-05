package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.EpgEventDao
import com.nextgen.iptv.data.local.entity.EpgEventEntity
import com.nextgen.iptv.domain.repository.EpgRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpgRepositoryImpl @Inject constructor(
    private val epgEventDao: EpgEventDao
) : EpgRepository {
    
    override fun getEventsByChannelId(channelId: String): Flow<List<EpgEventEntity>> = 
        epgEventDao.getByChannelId(channelId)
    
    override suspend fun getCurrentEvent(channelId: String, currentTime: Long): EpgEventEntity? = 
        epgEventDao.getCurrentEvent(channelId, currentTime)
    
    override suspend fun getUpcomingEvents(channelId: String, currentTime: Long, limit: Int): List<EpgEventEntity> = 
        epgEventDao.getUpcomingEvents(channelId, currentTime, limit)
    
    override suspend fun addEvent(event: EpgEventEntity) = epgEventDao.insert(event)
    
    override suspend fun addEvents(events: List<EpgEventEntity>) = epgEventDao.insertAll(events)
    
    override suspend fun deleteEventsByChannelId(channelId: String) = 
        epgEventDao.deleteByChannelId(channelId)
    
    override suspend fun deleteOldEvents(timestamp: Long) = epgEventDao.deleteOldEvents(timestamp)
    
    override suspend fun deleteAllEvents() = epgEventDao.deleteAll()
}
