package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.domain.repository.CategoryRepository
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.SettingsRepository
import com.nextgen.iptv.domain.repository.StreamRepository
import com.nextgen.iptv.domain.usecase.favorite.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LiveTvUiState(
    val streams: List<StreamWithFavorite> = emptyList(),
    val categories: List<CategoryEntity> = emptyList(),
    val selectedCategoryId: String? = null,
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

data class StreamWithFavorite(
    val stream: StreamEntity,
    val isFavorite: Boolean = false
)

@HiltViewModel
class LiveTvViewModel @Inject constructor(
    private val streamRepository: StreamRepository,
    private val categoryRepository: CategoryRepository,
    private val settingsRepository: SettingsRepository,
    private val providerRepository: ProviderRepository,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LiveTvUiState())
    val uiState: StateFlow<LiveTvUiState> = _uiState.asStateFlow()

    init {
        loadStreams()
    }

    private fun loadStreams() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                // Get providers
                val providers = providerRepository.getAllProviders().first()
                val allSelectedCategoryIds = mutableSetOf<String>()
                
                for (provider in providers) {
                    val selectedCats = settingsRepository.getSelectedLiveCategories(provider.id).first()
                    allSelectedCategoryIds.addAll(selectedCats)
                }

                // Load categories
                val categories = categoryRepository.getCategoriesByType("live").first()
                
                // Load streams and combine with favorites
                val streamsFlow = if (allSelectedCategoryIds.isEmpty()) {
                    streamRepository.getStreamsByType("live")
                } else {
                    streamRepository.getStreamsByCategories(allSelectedCategoryIds.toList())
                        .combine(com.nextgen.iptv.di.AppModule.provideFavoriteRepository(kotlinx.coroutines.runBlocking { 
                            com.nextgen.iptv.di.AppModule.provideFavoriteDao(com.nextgen.iptv.di.AppModule.provideDatabase(android.app.Application()).appDatabase()) 
                        }).getAllFavorites()) { streams, favorites ->
                            streams.filter { it.type == "live" }
                                .map { stream ->
                                    StreamWithFavorite(
                                        stream = stream,
                                        isFavorite = favorites.any { it.streamId == stream.id }
                                    )
                                }
                        }
                }
                
                streamsFlow.collect { streams ->
                    _uiState.update { state ->
                        state.copy(
                            categories = categories,
                            streams = streams,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun selectCategory(categoryId: String?) {
        _uiState.update { it.copy(selectedCategoryId = categoryId, isLoading = true) }

        viewModelScope.launch {
            try {
                val streamsFlow = if (categoryId == null) {
                    streamRepository.getStreamsByType("live")
                } else {
                    streamRepository.getStreamsByCategory(categoryId)
                }
                
                streamsFlow.combine(
                    com.nextgen.iptv.di.AppModule.provideFavoriteRepository(kotlinx.coroutines.runBlocking { 
                        com.nextgen.iptv.di.AppModule.provideFavoriteDao(com.nextgen.iptv.di.AppModule.provideDatabase(android.app.Application()).appDatabase()) 
                    }).getAllFavorites()
                ) { streams, favorites ->
                    streams.filter { it.type == "live" }
                        .map { stream ->
                            StreamWithFavorite(
                                stream = stream,
                                isFavorite = favorites.any { it.streamId == stream.id }
                            )
                        }
                }.collect { streamsWithFavorites ->
                    _uiState.update { 
                        it.copy(
                            streams = streamsWithFavorites,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun toggleFavorite(streamId: String) {
        viewModelScope.launch {
            try {
                val currentStream = _uiState.value.streams.find { it.stream.id == streamId }
                currentStream?.let {
                    toggleFavoriteUseCase(streamId, "live")
                    // Update UI immediately
                    _uiState.update { state ->
                        state.copy(
                            streams = state.streams.map { s ->
                                if (s.stream.id == streamId) {
                                    s.copy(isFavorite = !s.isFavorite)
                                } else s
                            }
                        )
                    }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query, isLoading = true) }

        viewModelScope.launch {
            if (query.isBlank()) {
                loadStreams()
            } else {
                try {
                    streamRepository.searchStreams(query)
                        .combine(
                            com.nextgen.iptv.di.AppModule.provideFavoriteRepository(kotlinx.coroutines.runBlocking { 
                                com.nextgen.iptv.di.AppModule.provideFavoriteDao(com.nextgen.iptv.di.AppModule.provideDatabase(android.app.Application()).appDatabase()) 
                            }).getAllFavorites()
                        ) { streams, favorites ->
                            streams.filter { it.type == "live" }
                                .map { stream ->
                                    StreamWithFavorite(
                                        stream = stream,
                                        isFavorite = favorites.any { it.streamId == stream.id }
                                    )
                                }
                        }.collect { streams ->
                            _uiState.update { 
                                it.copy(
                                    streams = streams,
                                    isLoading = false
                                )
                            }
                        }
                } catch (e: Exception) {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}

// Extension
private inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
    value = function(value)
}
