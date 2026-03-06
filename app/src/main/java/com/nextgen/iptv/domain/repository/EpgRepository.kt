package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.data.local.entity.EpgEventEntity
import kotlinx.coroutines.flow.Flow

interface EpgRepository {
    fun getEpgForChannel(channelId: String): Flow<List<EpgEventEntity>>
    suspend fun getCurrentEvent(channelId: String, currentTime: Long): EpgEventEntity?
    suspend fun getUpcomingEvents(channelId: String, currentTime: Long, limit: Int): List<EpgEventEntity>
    suspend fun insertEpg(epg: EpgEventEntity)
    suspend fun insertEpgList(epgList: List<EpgEventEntity>)
    suspend fun addEvents(events: List<EpgEventEntity>)
    suspend fun deleteEpgForProvider(providerId: String)
    suspend fun deleteOldEpg(currentTime: Long)
    suspend fun deleteOldEvents(timestamp: Long)
}
