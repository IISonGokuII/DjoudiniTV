package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.data.local.entity.VodProgressEntity
import com.nextgen.iptv.domain.repository.CategoryRepository
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.SettingsRepository
import com.nextgen.iptv.domain.repository.StreamRepository
import com.nextgen.iptv.domain.repository.VodProgressRepository
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

// Data class combining movie with its progress
data class MovieWithProgress(
    val movie: StreamEntity,
    val progressMs: Long = 0,
    val durationMs: Long = 0,
    val progressPercent: Int = 0
)

data class VodUiState(
    val movies: List<MovieWithProgress> = emptyList(),
    val continueWatching: List<MovieWithProgress> = emptyList(),
    val categories: List<CategoryEntity> = emptyList(),
    val selectedCategory: String = "",
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val hasSelectedCategories: Boolean = false
)

@HiltViewModel
class VodViewModel @Inject constructor(
    private val streamRepository: StreamRepository,
    private val categoryRepository: CategoryRepository,
    private val settingsRepository: SettingsRepository,
    private val providerRepository: ProviderRepository,
    private val vodProgressRepository: VodProgressRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(VodUiState())
    val uiState: StateFlow<VodUiState> = _uiState.asStateFlow()
    
    init {
        loadMovies()
    }
    
    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                // Get all providers
                val providers = providerRepository.getAllProviders().first()
                
                // Collect selected VOD categories from all providers
                val allSelectedCategoryIds = mutableSetOf<String>()
                for (provider in providers) {
                    val selectedCats = settingsRepository.getSelectedVodCategories(provider.id).first()
                    allSelectedCategoryIds.addAll(selectedCats)
                }
                
                // Load all VOD categories for display
                val allCategories = categoryRepository.getCategoriesByType("vod").first()
                
                // Load movies and combine with progress
                val moviesFlow = if (allSelectedCategoryIds.isEmpty()) {
                    kotlinx.coroutines.flow.flowOf(emptyList())
                } else {
                    streamRepository.getStreamsByCategories(allSelectedCategoryIds.toList())
                        .combine(vodProgressRepository.getAllProgress()) { streams, progressList ->
                            streams.filter { it.type == "vod" }
                                .map { stream ->
                                    val streamId = generateStreamId(stream.streamUrl)
                                    val progress = progressList.find { it.streamId == streamId }
                                    MovieWithProgress(
                                        movie = stream,
                                        progressMs = progress?.progressMs ?: 0,
                                        durationMs = progress?.durationMs ?: 0,
                                        progressPercent = if (progress != null && progress.durationMs > 0) {
                                            ((progress.progressMs * 100) / progress.durationMs).toInt()
                                        } else 0
                                    )
                                }
                        }
                }
                
                moviesFlow.collect { moviesWithProgress ->
                    // Separate continue watching (5-95% progress) from unwatched
                    val (continueWatching, unwatched) = moviesWithProgress.partition { 
                        it.progressPercent in 5..95 
                    }
                    
                    _uiState.update { state ->
                        state.copy(
                            categories = allCategories,
                            movies = if (state.searchQuery.isBlank() && state.selectedCategory.isBlank()) {
                                unwatched
                            } else moviesWithProgress,
                            continueWatching = continueWatching.sortedByDescending { it.progressMs },
                            isLoading = false,
                            hasSelectedCategories = allSelectedCategoryIds.isNotEmpty()
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
    
    fun selectCategory(categoryId: String) {
        _uiState.update { it.copy(selectedCategory = categoryId, isLoading = true) }
        
        viewModelScope.launch {
            if (categoryId.isEmpty()) {
                loadMovies()
            } else {
                // Show only this specific category with progress
                val progressList = vodProgressRepository.getAllProgress().first()
                streamRepository.getStreamsByCategory(categoryId).collect { movies ->
                    val moviesWithProgress = movies.filter { it.type == "vod" }.map { stream ->
                        val streamId = generateStreamId(stream.streamUrl)
                        val progress = progressList.find { it.streamId == streamId }
                        MovieWithProgress(
                            movie = stream,
                            progressMs = progress?.progressMs ?: 0,
                            durationMs = progress?.durationMs ?: 0,
                            progressPercent = if (progress != null && progress.durationMs > 0) {
                                ((progress.progressMs * 100) / progress.durationMs).toInt()
                            } else 0
                        )
                    }
                    _uiState.update { it.copy(movies = moviesWithProgress, isLoading = false) }
                }
            }
        }
    }
    
    fun searchMovies(query: String) {
        _uiState.update { it.copy(searchQuery = query, isLoading = true) }
        
        viewModelScope.launch {
            if (query.isBlank()) {
                loadMovies()
            } else {
                val providers = providerRepository.getAllProviders().first()
                val allSelectedCategoryIds = mutableSetOf<String>()
                for (provider in providers) {
                    val selectedCats = settingsRepository.getSelectedVodCategories(provider.id).first()
                    allSelectedCategoryIds.addAll(selectedCats)
                }
                
                val progressList = vodProgressRepository.getAllProgress().first()
                streamRepository.searchStreams(query).collect { movies ->
                    var vodMovies = movies.filter { it.type == "vod" }
                    
                    if (allSelectedCategoryIds.isNotEmpty()) {
                        vodMovies = vodMovies.filter { it.categoryId in allSelectedCategoryIds }
                    }
                    
                    val moviesWithProgress = vodMovies.map { stream ->
                        val streamId = generateStreamId(stream.streamUrl)
                        val progress = progressList.find { it.streamId == streamId }
                        MovieWithProgress(
                            movie = stream,
                            progressMs = progress?.progressMs ?: 0,
                            durationMs = progress?.durationMs ?: 0,
                            progressPercent = if (progress != null && progress.durationMs > 0) {
                                ((progress.progressMs * 100) / progress.durationMs).toInt()
                            } else 0
                        )
                    }
                    
                    _uiState.update { it.copy(movies = moviesWithProgress, isLoading = false) }
                }
            }
        }
    }
    
    fun refresh() {
        loadMovies()
    }
    
    companion object {
        fun generateStreamId(url: String): String {
            return url.hashCode().toString()
        }
    }
}

// Extension function for StateFlow update
private inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
    value = function(value)
}
