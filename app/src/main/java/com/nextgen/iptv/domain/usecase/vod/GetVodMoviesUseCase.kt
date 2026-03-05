package com.nextgen.iptv.domain.usecase.vod

import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.domain.repository.StreamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVodMoviesUseCase @Inject constructor(
    private val streamRepository: StreamRepository
) {
    operator fun invoke(): Flow<List<StreamEntity>> {
        return streamRepository.getStreamsByType("vod")
    }
}
