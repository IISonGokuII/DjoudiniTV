package com.nextgen.iptv.domain.usecase.favorite

import com.nextgen.iptv.data.local.entity.FavoriteEntity
import com.nextgen.iptv.domain.repository.FavoriteRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(streamId: String, streamType: String): Boolean {
        val isFavorite = favoriteRepository.isFavorite(streamId)
        
        return if (isFavorite) {
            favoriteRepository.removeFavoriteByStreamId(streamId)
            false
        } else {
            favoriteRepository.addFavorite(
                FavoriteEntity(streamId = streamId, streamType = streamType)
            )
            true
        }
    }
}
