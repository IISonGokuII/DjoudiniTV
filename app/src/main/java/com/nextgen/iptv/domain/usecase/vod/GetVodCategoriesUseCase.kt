package com.nextgen.iptv.domain.usecase.vod

import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVodCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<List<CategoryEntity>> {
        return categoryRepository.getCategoriesByProviderAndType("", "vod")
    }
}
