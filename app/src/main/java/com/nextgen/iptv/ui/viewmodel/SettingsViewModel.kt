package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.domain.model.AppSettings
import com.nextgen.iptv.domain.usecase.settings.GetSettingsUseCase
import com.nextgen.iptv.domain.usecase.settings.UpdateSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val settings: AppSettings = AppSettings(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState(isLoading = true))
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
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
}
