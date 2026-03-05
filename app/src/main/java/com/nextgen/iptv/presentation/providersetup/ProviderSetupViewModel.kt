package com.nextgen.iptv.presentation.providersetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.domain.model.ProviderType
import com.nextgen.iptv.domain.usecase.provider.AddProviderUseCase
import com.nextgen.iptv.domain.usecase.provider.SyncProviderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProviderSetupViewModel @Inject constructor(
    private val addProviderUseCase: AddProviderUseCase,
    private val syncProviderUseCase: SyncProviderUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProviderSetupUiState())
    val uiState: StateFlow<ProviderSetupUiState> = _uiState.asStateFlow()
    
    fun onServerUrlChange(url: String) {
        _uiState.update { it.copy(serverUrl = url, error = null) }
    }
    
    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username, error = null) }
    }
    
    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }
    
    fun onProviderNameChange(name: String) {
        _uiState.update { it.copy(providerName = name, error = null) }
    }
    
    fun onProviderTypeSelected(type: SetupProviderType) {
        _uiState.update { it.copy(selectedType = type, error = null) }
    }
    
    fun addProvider(onSuccess: () -> Unit) {
        val state = _uiState.value
        
        if (state.providerName.isBlank()) {
            _uiState.update { it.copy(error = "Bitte gib einen Namen ein") }
            return
        }
        
        val providerType = when (state.selectedType) {
            SetupProviderType.XTREAM -> {
                if (state.serverUrl.isBlank() || state.username.isBlank() || state.password.isBlank()) {
                    _uiState.update { it.copy(error = "Bitte alle Felder ausfüllen") }
                    return
                }
                ProviderType.XtreamCodes(
                    serverUrl = state.serverUrl,
                    username = state.username,
                    password = state.password
                )
            }
            SetupProviderType.M3U_URL -> {
                if (state.serverUrl.isBlank()) {
                    _uiState.update { it.copy(error = "Bitte M3U URL eingeben") }
                    return
                }
                ProviderType.M3UUrl(url = state.serverUrl)
            }
            SetupProviderType.M3U_LOCAL -> {
                _uiState.update { it.copy(error = "Lokale M3U noch nicht implementiert") }
                return
            }
        }
        
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            addProviderUseCase(state.providerName, providerType)
                .onSuccess { providerId ->
                    // Auto-sync after adding
                    syncProvider(providerId, onSuccess)
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            error = error.message ?: "Fehler beim Hinzufügen"
                        )
                    }
                }
        }
    }
    
    private fun syncProvider(providerId: String, onSuccess: () -> Unit) {
        syncProviderUseCase(providerId)
            .onEach { progress ->
                when (progress) {
                    is SyncProviderUseCase.SyncProgress.Starting -> {
                        _uiState.update { it.copy(syncStatus = "Synchronisiere...") }
                    }
                    is SyncProviderUseCase.SyncProgress.Progress -> {
                        _uiState.update { it.copy(syncStatus = progress.message) }
                    }
                    is SyncProviderUseCase.SyncProgress.Success -> {
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                syncStatus = "Synchronisation erfolgreich"
                            )
                        }
                        onSuccess()
                    }
                    is SyncProviderUseCase.SyncProgress.Error -> {
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                error = progress.message
                            )
                        }
                    }
                    else -> {}
                }
            }
            .launchIn(viewModelScope)
    }
}

data class ProviderSetupUiState(
    val selectedType: SetupProviderType = SetupProviderType.XTREAM,
    val providerName: String = "",
    val serverUrl: String = "",
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val syncStatus: String? = null
)

enum class SetupProviderType {
    XTREAM, M3U_URL, M3U_LOCAL
}
