package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.data.local.entity.EpgEntity
import kotlinx.coroutines.flow.Flow

interface EpgRepository {
    fun getEpgForChannel(channelId: String): Flow<List<EpgEntity>>
    fun getCurrentEpgForChannel(channelId: String, currentTime: Long): Flow<EpgEntity?>
    suspend fun insertEpg(epg: EpgEntity)
    suspend fun insertEpgList(epgList: List<EpgEntity>)
    suspend fun deleteEpgForProvider(providerId: String)
    suspend fun deleteOldEpg(currentTime: Long)
}
