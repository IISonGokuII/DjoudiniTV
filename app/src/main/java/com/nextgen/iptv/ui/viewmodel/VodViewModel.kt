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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VodUiState(
    val movies: List<StreamEntity> = emptyList(),
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
    private val providerRepository: ProviderRepository
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
                
                // Filter movies based on selected categories
                val movies = if (allSelectedCategoryIds.isEmpty()) {
                    // No categories selected - show nothing
                    emptyList()
                } else {
                    // Load only movies from selected categories
                    streamRepository.getStreamsByCategories(allSelectedCategoryIds.toList()).first()
                        .filter { it.type == "vod" }
                }
                
                _uiState.update { state ->
                    state.copy(
                        categories = allCategories,
                        movies = movies,
                        isLoading = false,
                        hasSelectedCategories = allSelectedCategoryIds.isNotEmpty()
                    )
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
                // Show all movies from selected categories
                loadMovies()
            } else {
                // Show only this specific category
                streamRepository.getStreamsByCategory(categoryId).collect { movies ->
                    _uiState.update { it.copy(movies = movies.filter { it.type == "vod" }, isLoading = false) }
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
                streamRepository.searchStreams(query).collect { movies ->
                    // Filter only VOD results and by selected categories
                    var vodMovies = movies.filter { it.type == "vod" }
                    
                    // Also filter by selected categories if any
                    val providers = providerRepository.getAllProviders().first()
                    val allSelectedCategoryIds = mutableSetOf<String>()
                    for (provider in providers) {
                        val selectedCats = settingsRepository.getSelectedVodCategories(provider.id).first()
                        allSelectedCategoryIds.addAll(selectedCats)
                    }
                    
                    if (allSelectedCategoryIds.isNotEmpty()) {
                        vodMovies = vodMovies.filter { it.categoryId in allSelectedCategoryIds }
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
