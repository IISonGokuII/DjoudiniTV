package com.nextgen.iptv.presentation.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.domain.usecase.favorite.GetFavoritesUseCase
import com.nextgen.iptv.domain.usecase.favorite.ToggleFavoriteUseCase
import com.nextgen.iptv.domain.usecase.stream.GetStreamsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    getStreamsByCategoryUseCase: GetStreamsByCategoryUseCase,
    getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SeriesUiState())
    val uiState: StateFlow<SeriesUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    
    init {
        // Load series episodes
        getStreamsByCategoryUseCase("")
            .onEach { streams ->
                val seriesStreams = streams.filter { it.type == "series_episode" }
                _uiState.update { 
                    it.copy(
                        series = seriesStreams,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
        
        // Load favorites
        getFavoritesUseCase.byType("series")
            .onEach { favorites ->
                _uiState.update {
                    it.copy(favoriteIds = favorites.map { f -> f.streamId }.toSet())
                }
            }
            .launchIn(viewModelScope)
    }
    
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
    
    fun toggleFavorite(streamId: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase(streamId, "series")
        }
    }
}

data class SeriesUiState(
    val series: List<StreamEntity> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
    val isLoading: Boolean = true
)
