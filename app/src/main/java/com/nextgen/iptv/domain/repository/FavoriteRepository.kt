package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorites(): Flow<List<FavoriteEntity>>
    fun getFavoritesByType(type: String): Flow<List<FavoriteEntity>>
    suspend fun isFavorite(streamId: String): Boolean
    suspend fun addFavorite(favorite: FavoriteEntity)
    suspend fun removeFavorite(favorite: FavoriteEntity)
    suspend fun removeFavoriteByStreamId(streamId: String)
    suspend fun deleteAllFavorites()
}
