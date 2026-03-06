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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LiveTvUiState(
    val categories: List<CategoryEntity> = emptyList(),
    val streams: List<StreamEntity> = emptyList(),
    val selectedCategoryId: String? = null, // For UI filtering
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val hasSelectedCategories: Boolean = false // Whether user has made a selection
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
                
                // Load all categories for display
                val allCategories = categoryRepository.getCategoriesByType("live").first()
                
                // Filter streams based on selected categories
                val streams = if (allSelectedCategoryIds.isEmpty()) {
                    // No categories selected - show nothing (user must select in settings)
                    emptyList()
                } else {
                    // Load only streams from selected categories
                    streamRepository.getStreamsByCategories(allSelectedCategoryIds.toList()).first()
                }
                
                _uiState.update { state ->
                    state.copy(
                        categories = allCategories,
                        streams = streams,
                        isLoading = false,
                        hasSelectedCategories = allSelectedCategoryIds.isNotEmpty()
                    )
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
