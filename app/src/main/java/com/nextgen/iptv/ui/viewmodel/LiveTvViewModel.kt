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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LiveTvUiState(
    val categories: List<CategoryEntity> = emptyList(),
    val streams: List<StreamEntity> = emptyList(),
    val selectedCategoryId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

@HiltViewModel
class LiveTvViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val streamRepository: StreamRepository
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
                combine(
                    categoryRepository.getCategoriesByType("live"),
                    streamRepository.getStreamsByType("live"),
                    _uiState
                ) { categories, streams, state ->
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
