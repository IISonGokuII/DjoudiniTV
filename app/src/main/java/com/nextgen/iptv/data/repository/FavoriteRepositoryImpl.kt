package com.nextgen.iptv.data.repository

import com.nextgen.iptv.data.local.dao.FavoriteDao
import com.nextgen.iptv.data.local.entity.FavoriteEntity
import com.nextgen.iptv.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {
    
    override fun getAllFavorites(): Flow<List<FavoriteEntity>> = favoriteDao.getAll()
    
    override fun getFavoritesByType(type: String): Flow<List<FavoriteEntity>> = 
        favoriteDao.getByType(type)
    
    override suspend fun isFavorite(streamId: String): Boolean = favoriteDao.isFavorite(streamId)
    
    override suspend fun addFavorite(favorite: FavoriteEntity) = favoriteDao.insert(favorite)
    
    override suspend fun removeFavorite(favorite: FavoriteEntity) = favoriteDao.delete(favorite)
    
    override suspend fun removeFavoriteByStreamId(streamId: String) = 
        favoriteDao.deleteByStreamId(streamId)
    
    override suspend fun deleteAllFavorites() = favoriteDao.deleteAll()
}
