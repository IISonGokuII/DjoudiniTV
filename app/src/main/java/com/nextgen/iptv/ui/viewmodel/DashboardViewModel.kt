package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.usecase.provider.GetProvidersWithStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val providers: List<ProviderStatus> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class ProviderStatus(
    val id: String,
    val name: String,
    val type: String,
    val isConnected: Boolean,
    val channelCount: Int = 0,
    val lastSync: Long? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val providerRepository: ProviderRepository,
    private val getProvidersWithStatusUseCase: GetProvidersWithStatusUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState(isLoading = true))
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadProviders()
    }

    fun loadProviders() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                getProvidersWithStatusUseCase().collect { providers ->
                    _uiState.update { 
                        it.copy(
                            providers = providers,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun deleteProvider(providerId: String) {
        viewModelScope.launch {
            try {
                providerRepository.deleteProvider(providerId)
                loadProviders()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
