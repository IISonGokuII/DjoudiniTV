package com.nextgen.iptv.domain.usecase.favorite

import com.nextgen.iptv.data.local.entity.FavoriteEntity
import com.nextgen.iptv.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<FavoriteEntity>> {
        return favoriteRepository.getAllFavorites()
    }
    
    fun byType(type: String): Flow<List<FavoriteEntity>> {
        return favoriteRepository.getFavoritesByType(type)
    }
}
