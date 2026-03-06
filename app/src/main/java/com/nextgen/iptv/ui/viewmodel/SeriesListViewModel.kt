package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.data.local.entity.SeriesEntity
import com.nextgen.iptv.domain.repository.CategoryRepository
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.SeriesRepository
import com.nextgen.iptv.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SeriesListUiState(
    val isLoading: Boolean = true,
    val categories: List<CategoryEntity> = emptyList(),
    val series: List<SeriesEntity> = emptyList(),
    val selectedCategoryId: String? = null,
    val error: String? = null,
    val selectedCategoryIds: Set<String> = emptySet() // Categories selected during onboarding
)

@HiltViewModel
class SeriesListViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val categoryRepository: CategoryRepository,
    private val settingsRepository: SettingsRepository,
    private val providerRepository: ProviderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeriesListUiState())
    val uiState: StateFlow<SeriesListUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                // Get all providers
                val providers = providerRepository.getAllProviders().first()
                
                // Collect selected series categories from all providers
                val allSelectedCategoryIds = mutableSetOf<String>()
                for (provider in providers) {
                    val selectedCats = settingsRepository.getSelectedSeriesCategories(provider.id).first()
                    allSelectedCategoryIds.addAll(selectedCats)
                }
                
                val categories = categoryRepository.getCategoriesByType("series").first()
                
                // If no categories selected, show all series
                // Otherwise filter by selected categories
                val series = if (allSelectedCategoryIds.isEmpty()) {
                    seriesRepository.getAllSeries().first()
                } else {
                    seriesRepository.getSeriesByCategories(allSelectedCategoryIds.toList()).first()
                }
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    categories = categories,
                    series = series,
                    selectedCategoryIds = allSelectedCategoryIds,
                    selectedCategoryId = categories.firstOrNull()?.id
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun selectCategory(categoryId: String?) {
        _uiState.value = _uiState.value.copy(selectedCategoryId = categoryId)
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun refresh() {
        loadData()
    }

    val filteredSeries: StateFlow<List<SeriesEntity>> = combine(
        _uiState,
        _searchQuery
    ) { state, query ->
        when {
            query.isNotBlank() -> {
                state.series.filter { it.name.contains(query, ignoreCase = true) }
            }
            state.selectedCategoryId != null -> {
                state.series.filter { it.categoryId == state.selectedCategoryId }
            }
            else -> state.series
        }
    }.stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), emptyList())
}
