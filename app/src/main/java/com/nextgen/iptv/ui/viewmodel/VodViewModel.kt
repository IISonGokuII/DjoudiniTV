package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.domain.repository.CategoryRepository
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.SettingsRepository
import com.nextgen.iptv.domain.repository.StreamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VodUiState(
    val movies: List<StreamEntity> = emptyList(),
    val categories: List<CategoryEntity> = emptyList(),
    val selectedCategory: String = "",
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val selectedCategoryIds: Set<String> = emptySet() // Categories selected during onboarding
)

@HiltViewModel
class VodViewModel @Inject constructor(
    private val streamRepository: StreamRepository,
    private val categoryRepository: CategoryRepository,
    private val settingsRepository: SettingsRepository,
    private val providerRepository: ProviderRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(VodUiState())
    val uiState: StateFlow<VodUiState> = _uiState.asStateFlow()
    
    init {
        loadMovies()
        loadCategories()
    }
    
    private fun loadMovies() {
        viewModelScope.launch {
            // Get all providers
            val providers = providerRepository.getAllProviders().first()
            
            // Collect selected VOD categories from all providers
            val allSelectedCategoryIds = mutableSetOf<String>()
            for (provider in providers) {
                val selectedCats = settingsRepository.getSelectedVodCategories(provider.id).first()
                allSelectedCategoryIds.addAll(selectedCats)
            }
            
            _uiState.update { it.copy(selectedCategoryIds = allSelectedCategoryIds) }
            
            // If no categories selected, show all VOD
            // Otherwise filter by selected categories
            val moviesFlow = if (allSelectedCategoryIds.isEmpty()) {
                streamRepository.getStreamsByType("vod")
            } else {
                streamRepository.getStreamsByTypeAndCategories("vod", allSelectedCategoryIds.toList())
            }
            
            moviesFlow.collect { movies ->
                _uiState.update { 
                    it.copy(
                        movies = movies,
                        isLoading = false
                    )
                }
            }
        }
    }
    
    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getCategoriesByType("vod").collect { categories ->
                _uiState.update { it.copy(categories = categories) }
            }
        }
    }
    
    fun selectCategory(categoryId: String) {
        _uiState.update { it.copy(selectedCategory = categoryId, isLoading = true) }
        
        viewModelScope.launch {
            if (categoryId.isEmpty()) {
                // Show movies from selected categories (or all if none selected)
                val selectedCats = _uiState.value.selectedCategoryIds.toList()
                val moviesFlow = if (selectedCats.isEmpty()) {
                    streamRepository.getStreamsByType("vod")
                } else {
                    streamRepository.getStreamsByTypeAndCategories("vod", selectedCats)
                }
                moviesFlow.collect { movies ->
                    _uiState.update { it.copy(movies = movies, isLoading = false) }
                }
            } else {
                // Show only this specific category
                streamRepository.getStreamsByCategory(categoryId).collect { movies ->
                    _uiState.update { it.copy(movies = movies, isLoading = false) }
                }
            }
        }
    }
    
    fun searchMovies(query: String) {
        _uiState.update { it.copy(searchQuery = query, isLoading = true) }
        
        viewModelScope.launch {
            if (query.isBlank()) {
                // Show movies from selected categories (or all if none selected)
                val selectedCats = _uiState.value.selectedCategoryIds.toList()
                val moviesFlow = if (selectedCats.isEmpty()) {
                    streamRepository.getStreamsByType("vod")
                } else {
                    streamRepository.getStreamsByTypeAndCategories("vod", selectedCats)
                }
                moviesFlow.collect { movies ->
                    _uiState.update { it.copy(movies = movies, isLoading = false) }
                }
            } else {
                streamRepository.searchStreams(query).collect { movies ->
                    // Filter only VOD results and by selected categories
                    var vodMovies = movies.filter { it.type == "vod" }
                    val selectedCats = _uiState.value.selectedCategoryIds
                    if (selectedCats.isNotEmpty()) {
                        vodMovies = vodMovies.filter { it.categoryId in selectedCats }
                    }
                    _uiState.update { it.copy(movies = vodMovies, isLoading = false) }
                }
            }
        }
    }
    
    fun refresh() {
        loadMovies()
    }
}

// Extension function for StateFlow update
private inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
    value = function(value)
}
