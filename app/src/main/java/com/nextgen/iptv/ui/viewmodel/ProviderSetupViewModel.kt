package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.domain.model.ProviderType
import com.nextgen.iptv.domain.usecase.provider.AddProviderUseCase
import com.nextgen.iptv.domain.usecase.provider.SyncProviderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProviderSetupUiState(
    val name: String = "",
    val selectedType: SetupProviderType = SetupProviderType.XTREAM,
    val serverUrl: String = "",
    val username: String = "",
    val password: String = "",
    val m3uUrl: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

enum class SetupProviderType {
    XTREAM, M3U_URL, M3U_LOCAL
}

@HiltViewModel
class ProviderSetupViewModel @Inject constructor(
    private val addProviderUseCase: AddProviderUseCase,
    private val syncProviderUseCase: SyncProviderUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProviderSetupUiState())
    val uiState: StateFlow<ProviderSetupUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, error = null) }
    }

    fun onTypeChange(type: SetupProviderType) {
        _uiState.update { it.copy(selectedType = type, error = null) }
    }

    fun onServerUrlChange(url: String) {
        _uiState.update { it.copy(serverUrl = url, error = null) }
    }

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username, error = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    fun onM3uUrlChange(url: String) {
        _uiState.update { it.copy(m3uUrl = url, error = null) }
    }

    fun addProvider(onSuccess: () -> Unit = {}) {
        val state = _uiState.value
        
        // Validate inputs
        if (state.name.isBlank()) {
            _uiState.update { it.copy(error = "Name ist erforderlich") }
            return
        }
        
        val providerType = when (state.selectedType) {
            SetupProviderType.XTREAM -> {
                if (state.serverUrl.isBlank() || state.username.isBlank() || state.password.isBlank()) {
                    _uiState.update { it.copy(error = "Server, Benutzername und Passwort sind erforderlich") }
                    return
                }
                // Normalize URL - add http:// if missing
                val normalizedUrl = normalizeUrl(state.serverUrl)
                ProviderType.XtreamCodes(
                    serverUrl = normalizedUrl,
                    username = state.username,
                    password = state.password
                )
            }
            SetupProviderType.M3U_URL -> {
                if (state.m3uUrl.isBlank()) {
                    _uiState.update { it.copy(error = "M3U URL ist erforderlich") }
                    return
                }
                val normalizedUrl = normalizeUrl(state.m3uUrl)
                ProviderType.M3uUrl(normalizedUrl)
            }
            SetupProviderType.M3U_LOCAL -> {
                _uiState.update { it.copy(error = "Lokale M3U-Dateien werden noch nicht unterstützt") }
                return
            }
        }
        
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            addProviderUseCase(state.name, providerType)
                .onSuccess { providerId ->
                    _uiState.update { it.copy(isLoading = false, success = true) }
                    // Start sync
                    syncProviderUseCase(providerId).collect { progress ->
                        when (progress) {
                            is SyncProviderUseCase.SyncProgress.Success -> {
                                onSuccess()
                            }
                            is SyncProviderUseCase.SyncProgress.Error -> {
                                _uiState.update { it.copy(error = progress.message) }
                            }
                            else -> { /* Handle other progress states */ }
                        }
                    }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }
    
    private fun normalizeUrl(url: String): String {
        val trimmed = url.trim()
        return when {
            trimmed.startsWith("http://", ignoreCase = true) -> trimmed
            trimmed.startsWith("https://", ignoreCase = true) -> trimmed
            else -> "http://$trimmed"
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
    
    fun reset() {
        _uiState.value = ProviderSetupUiState()
    }
}
