package com.nextgen.iptv.domain.usecase.stream

import androidx.paging.PagingSource
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.domain.repository.StreamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStreamsByCategoryUseCase @Inject constructor(
    private val streamRepository: StreamRepository
) {
    operator fun invoke(categoryId: String): Flow<List<StreamEntity>> {
        return streamRepository.getStreamsByCategory(categoryId)
    }
    
    fun paged(categoryId: String): PagingSource<Int, StreamEntity> {
        return streamRepository.getStreamsByCategoryPaged(categoryId)
    }
}
