package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.domain.repository.CategoryRepository
import com.nextgen.iptv.domain.repository.StreamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VodUiState(
    val movies: List<StreamEntity> = emptyList(),
    val categories: List<CategoryEntity> = emptyList(),
    val selectedCategory: String = "",
    val searchQuery: String = "",
    val isLoading: Boolean = true
)

@HiltViewModel
class VodViewModel @Inject constructor(
    private val streamRepository: StreamRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(VodUiState())
    val uiState: StateFlow<VodUiState> = _uiState.asStateFlow()
    
    init {
        loadMovies()
        loadCategories()
    }
    
    private fun loadMovies() {
        viewModelScope.launch {
            streamRepository.getStreamsByType("vod").collect { movies ->
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
                streamRepository.getStreamsByType("vod").collect { movies ->
                    _uiState.update { it.copy(movies = movies, isLoading = false) }
                }
            } else {
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
                streamRepository.getStreamsByType("vod").collect { movies ->
                    _uiState.update { it.copy(movies = movies, isLoading = false) }
                }
            } else {
                streamRepository.searchStreams(query).collect { movies ->
                    // Filter only VOD results
                    val vodMovies = movies.filter { it.type == "vod" }
                    _uiState.update { it.copy(movies = vodMovies, isLoading = false) }
                }
            }
        }
    }
}

// Extension function for StateFlow update
private inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
    value = function(value)
}
