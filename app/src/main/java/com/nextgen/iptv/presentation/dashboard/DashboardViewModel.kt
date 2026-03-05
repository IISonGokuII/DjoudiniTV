package com.nextgen.iptv.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.ProviderEntity
import com.nextgen.iptv.domain.usecase.provider.GetProvidersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getProvidersUseCase: GetProvidersUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        getProvidersUseCase()
            .onEach { providers ->
                _uiState.update { 
                    it.copy(
                        providers = providers,
                        hasProviders = providers.isNotEmpty()
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}

data class DashboardUiState(
    val providers: List<ProviderEntity> = emptyList(),
    val hasProviders: Boolean = false,
    val isLoading: Boolean = false
)
