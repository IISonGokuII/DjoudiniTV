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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LiveTvUiState(
    val categories: List<CategoryEntity> = emptyList(),
    val streams: List<StreamEntity> = emptyList(),
    val selectedCategoryId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val selectedCategoryIds: Set<String> = emptySet() // Categories selected during onboarding
)

@HiltViewModel
class LiveTvViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val streamRepository: StreamRepository,
    private val settingsRepository: SettingsRepository,
    private val providerRepository: ProviderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LiveTvUiState(isLoading = true))
    val uiState: StateFlow<LiveTvUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // Get all providers
                val providers = providerRepository.getAllProviders().first()
                
                // Collect selected categories from all providers
                val allSelectedCategoryIds = mutableSetOf<String>()
                for (provider in providers) {
                    val selectedCats = settingsRepository.getSelectedLiveCategories(provider.id).first()
                    allSelectedCategoryIds.addAll(selectedCats)
                }
                
                _uiState.update { it.copy(selectedCategoryIds = allSelectedCategoryIds) }
                
                // If no categories selected, show all streams
                // Otherwise filter by selected categories
                val streamsFlow = if (allSelectedCategoryIds.isEmpty()) {
                    streamRepository.getStreamsByType("live")
                } else {
                    streamRepository.getStreamsByTypeAndCategories("live", allSelectedCategoryIds.toList())
                }
                
                combine(
                    categoryRepository.getCategoriesByType("live"),
                    streamsFlow,
                    _uiState
                ) { categories, streams, state ->
                    // Further filter by UI selected category if any
                    val filteredStreams = if (state.selectedCategoryId != null) {
                        streams.filter { it.categoryId == state.selectedCategoryId }
                    } else {
                        streams
                    }.filter { 
                        it.name.contains(state.searchQuery, ignoreCase = true) 
                    }
                    
                    state.copy(
                        categories = categories,
                        streams = filteredStreams,
                        isLoading = false
                    )
                }.collect { newState ->
                    _uiState.value = newState
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(isLoading = false, error = e.message) 
                }
            }
        }
    }

    fun selectCategory(categoryId: String?) {
        _uiState.update { it.copy(selectedCategoryId = categoryId) }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }
}

// Extension function for StateFlow update
private inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
    value = function(value)
}
