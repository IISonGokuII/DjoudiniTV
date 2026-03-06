package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.domain.repository.FavoriteRepository
import com.nextgen.iptv.domain.repository.SeriesRepository
import com.nextgen.iptv.domain.repository.StreamRepository
import com.nextgen.iptv.domain.usecase.favorite.GetFavoritesUseCase
import com.nextgen.iptv.domain.usecase.favorite.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FavoriteWithDetails(
    val streamId: String,
    val name: String,
    val type: String,
    val streamUrl: String?,
    val logoUrl: String?
)

data class FavoritesUiState(
    val favorites: List<FavoriteWithDetails> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val streamRepository: StreamRepository,
    private val seriesRepository: SeriesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                getFavoritesUseCase().collect { favoriteEntities ->
                    val favoritesWithDetails = favoriteEntities.mapNotNull { favorite ->
                        when (favorite.streamType) {
                            "live", "vod" -> {
                                // For live TV and VOD, get from stream repository
                                val stream = streamRepository.getStreamById(favorite.streamId)
                                stream?.let {
                                    FavoriteWithDetails(
                                        streamId = favorite.streamId,
                                        name = it.name,
                                        type = favorite.streamType,
                                        streamUrl = it.streamUrl,
                                        logoUrl = it.logoUrl
                                    )
                                }
                            }
                            "series" -> {
                                // For series, get from series repository
                                val series = seriesRepository.getSeriesById(favorite.streamId)
                                series?.let {
                                    FavoriteWithDetails(
                                        streamId = favorite.streamId,
                                        name = it.name,
                                        type = "series",
                                        streamUrl = null, // Series don't have direct stream URL
                                        logoUrl = it.posterUrl
                                    )
                                }
                            }
                            else -> null
                        }
                    }
                    
                    _uiState.update {
                        it.copy(
                            favorites = favoritesWithDetails,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun removeFavorite(streamId: String) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(streamId, "") // Remove favorite
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun refresh() {
        loadFavorites()
    }
}

// Extension function
private inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
    value = function(value)
}
