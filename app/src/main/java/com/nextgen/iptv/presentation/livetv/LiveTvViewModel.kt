package com.nextgen.iptv.presentation.livetv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.domain.usecase.stream.GetLiveCategoriesUseCase
import com.nextgen.iptv.domain.usecase.stream.GetStreamsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LiveTvViewModel @Inject constructor(
    getLiveCategoriesUseCase: GetLiveCategoriesUseCase,
    private val getStreamsByCategoryUseCase: GetStreamsByCategoryUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LiveTvUiState())
    val uiState: StateFlow<LiveTvUiState> = _uiState.asStateFlow()
    
    init {
        getLiveCategoriesUseCase()
            .onEach { categories ->
                _uiState.update { 
                    it.copy(
                        categories = categories,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }
    
    fun selectCategory(categoryId: String) {
        _uiState.update { it.copy(selectedCategoryId = categoryId, isLoading = true) }
        
        getStreamsByCategoryUseCase(categoryId)
            .onEach { streams ->
                _uiState.update {
                    it.copy(
                        streams = streams,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }
    
    fun selectStream(stream: StreamEntity) {
        _uiState.update { it.copy(selectedStream = stream) }
    }
}

data class LiveTvUiState(
    val categories: List<CategoryEntity> = emptyList(),
    val streams: List<StreamEntity> = emptyList(),
    val selectedCategoryId: String? = null,
    val selectedStream: StreamEntity? = null,
    val isLoading: Boolean = true
)
