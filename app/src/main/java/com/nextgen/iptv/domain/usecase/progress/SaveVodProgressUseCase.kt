package com.nextgen.iptv.domain.usecase.progress

import com.nextgen.iptv.data.local.entity.VodProgressEntity
import com.nextgen.iptv.domain.repository.VodProgressRepository
import javax.inject.Inject

class SaveVodProgressUseCase @Inject constructor(
    private val vodProgressRepository: VodProgressRepository
) {
    suspend operator fun invoke(
        streamId: String,
        progressMs: Long,
        durationMs: Long
    ) {
        vodProgressRepository.saveProgress(
            VodProgressEntity(
                streamId = streamId,
                progressMs = progressMs,
                durationMs = durationMs,
                lastWatched = System.currentTimeMillis()
            )
        )
    }
}
