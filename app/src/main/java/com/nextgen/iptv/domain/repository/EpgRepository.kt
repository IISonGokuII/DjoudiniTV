package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.data.local.entity.EpgEventEntity
import kotlinx.coroutines.flow.Flow

interface EpgRepository {
    fun getEventsByChannelId(channelId: String): Flow<List<EpgEventEntity>>
    suspend fun getCurrentEvent(channelId: String, currentTime: Long): EpgEventEntity?
    suspend fun getUpcomingEvents(channelId: String, currentTime: Long, limit: Int): List<EpgEventEntity>
    suspend fun addEvent(event: EpgEventEntity)
    suspend fun addEvents(events: List<EpgEventEntity>)
    suspend fun deleteEventsByChannelId(channelId: String)
    suspend fun deleteOldEvents(timestamp: Long)
    suspend fun deleteAllEvents()
}
