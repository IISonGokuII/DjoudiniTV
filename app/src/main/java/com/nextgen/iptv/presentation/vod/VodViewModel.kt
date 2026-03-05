package com.nextgen.iptv.presentation.vod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.domain.usecase.favorite.GetFavoritesUseCase
import com.nextgen.iptv.domain.usecase.favorite.ToggleFavoriteUseCase
import com.nextgen.iptv.domain.usecase.vod.GetVodMoviesUseCase
import com.nextgen.iptv.domain.usecase.progress.GetVodProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VodViewModel @Inject constructor(
    getVodMoviesUseCase: GetVodMoviesUseCase,
    getFavoritesUseCase: GetFavoritesUseCase,
    getVodProgressUseCase: GetVodProgressUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(VodUiState())
    val uiState: StateFlow<VodUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    init {
        combine(
            getVodMoviesUseCase(),
            getFavoritesUseCase(),
            getVodProgressUseCase(),
            _searchQuery
        ) { movies, favorites, progress, query ->
            val favoriteIds = favorites.map { it.streamId }.toSet()
            val progressMap = progress.associateBy { it.streamId }
            
            val filteredMovies = if (query.isBlank()) {
                movies
            } else {
                movies.filter { it.name.contains(query, ignoreCase = true) }
            }
            
            VodUiState(
                movies = filteredMovies,
                favoriteIds = favoriteIds,
                progressMap = progressMap,
                isLoading = false
            )
        }.launchIn(viewModelScope)
    }
    
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
    
    fun toggleFavorite(streamId: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase(streamId, "vod")
        }
    }
}

data class VodUiState(
    val movies: List<StreamEntity> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
    val progressMap: Map<String, com.nextgen.iptv.data.local.entity.VodProgressEntity> = emptyMap(),
    val isLoading: Boolean = true
)
