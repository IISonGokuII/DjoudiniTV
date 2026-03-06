package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.domain.model.AppSettings
import com.nextgen.iptv.data.local.entity.ProviderEntity
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.SettingsRepository
import com.nextgen.iptv.domain.usecase.provider.SyncProviderUseCase
import com.nextgen.iptv.domain.usecase.provider.SyncProgress
import com.nextgen.iptv.domain.usecase.settings.GetSettingsUseCase
import com.nextgen.iptv.domain.usecase.settings.UpdateSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val settings: AppSettings = AppSettings(),
    val providers: List<ProviderEntity> = emptyList(),
    val isLoading: Boolean = false,
    val isSyncing: Boolean = false,
    val syncMessage: String = "",
    val syncProgress: Int = 0,
    val error: String? = null
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    private val providerRepository: ProviderRepository,
    private val settingsRepository: SettingsRepository,
    private val syncProviderUseCase: SyncProviderUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState(isLoading = true))
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
        loadProviders()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            getSettingsUseCase().collect { settings ->
                _uiState.update {
                    it.copy(
                        settings = settings,
                        isLoading = false
                    )
                }
            }
        }
    }
    
    private fun loadProviders() {
        viewModelScope.launch {
            providerRepository.getAllProviders().collect { providers ->
                _uiState.update { it.copy(providers = providers) }
            }
        }
    }

    fun updatePlayerBufferSize(sizeMs: Int) {
        viewModelScope.launch {
            val current = _uiState.value.settings
            updateSettingsUseCase(current.copy(playerBufferSizeMs = sizeMs))
        }
    }

    fun updateEpgRefreshInterval(hours: Int) {
        viewModelScope.launch {
            val current = _uiState.value.settings
            updateSettingsUseCase(current.copy(epgRefreshIntervalHours = hours))
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            val current = _uiState.value.settings
            updateSettingsUseCase(current.copy(useDarkMode = enabled))
        }
    }

    fun updateStreamFormat(format: String) {
        viewModelScope.launch {
            val current = _uiState.value.settings
            updateSettingsUseCase(current.copy(preferredStreamFormat = format))
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            // TODO: Implement cache clearing
        }
    }
    
    fun syncProviderFull(providerId: String) {
        viewModelScope.launch {
            _uiState.update { 
                it.copy(
                    isSyncing = true,
                    syncMessage = "Starte vollstandige Synchronisation...",
                    syncProgress = 0
                )
            }
            
            syncProviderUseCase(providerId).collect { progress ->
                when (progress) {
                    is SyncProgress.Starting -> {
                        _uiState.update { 
                            it.copy(
                                syncMessage = "Synchronisation wird gestartet...",
                                syncProgress = 5
                            )
                        }
                    }
                    is SyncProgress.Progress -> {
                        _uiState.update { 
                            it.copy(
                                syncMessage = progress.message,
                                syncProgress = progress.percent
                            )
                        }
                    }
                    is SyncProgress.Success -> {
                        _uiState.update { 
                            it.copy(
                                isSyncing = false,
                                syncMessage = "Synchronisation abgeschlossen!",
                                syncProgress = 100
                            )
                        }
                    }
                    is SyncProgress.Error -> {
                        _uiState.update { 
                            it.copy(
                                isSyncing = false,
                                error = progress.message
                            )
                        }
                    }
                }
            }
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
