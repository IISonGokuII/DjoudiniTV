package com.nextgen.iptv.domain.usecase.epg

import com.nextgen.iptv.data.local.entity.EpgEventEntity
import com.nextgen.iptv.domain.repository.EpgRepository
import javax.inject.Inject

class GetCurrentEpgUseCase @Inject constructor(
    private val epgRepository: EpgRepository
) {
    suspend operator fun invoke(channelId: String): EpgEventEntity? {
        return epgRepository.getCurrentEvent(channelId, System.currentTimeMillis())
    }
}
