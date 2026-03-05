package com.nextgen.iptv.domain.usecase.epg

import com.nextgen.iptv.data.local.entity.EpgEventEntity
import com.nextgen.iptv.domain.repository.EpgRepository
import javax.inject.Inject

class GetUpcomingEpgUseCase @Inject constructor(
    private val epgRepository: EpgRepository
) {
    suspend operator fun invoke(channelId: String, limit: Int = 3): List<EpgEventEntity> {
        return epgRepository.getUpcomingEvents(channelId, System.currentTimeMillis(), limit)
    }
}
