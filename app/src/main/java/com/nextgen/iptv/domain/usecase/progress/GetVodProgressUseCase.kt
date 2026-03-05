package com.nextgen.iptv.domain.usecase.progress

import com.nextgen.iptv.data.local.entity.VodProgressEntity
import com.nextgen.iptv.domain.repository.VodProgressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVodProgressUseCase @Inject constructor(
    private val vodProgressRepository: VodProgressRepository
) {
    operator fun invoke(): Flow<List<VodProgressEntity>> {
        return vodProgressRepository.getAllProgress()
    }
    
    suspend fun getProgressMs(streamId: String): Long? {
        return vodProgressRepository.getProgressMs(streamId)
    }
}
