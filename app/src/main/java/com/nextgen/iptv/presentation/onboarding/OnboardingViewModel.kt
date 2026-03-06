package com.nextgen.iptv.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.domain.model.ProviderType
import com.nextgen.iptv.domain.usecase.provider.AddProviderUseCase
import com.nextgen.iptv.domain.usecase.provider.SyncProgress
import com.nextgen.iptv.domain.usecase.provider.SyncProviderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class OnboardingStep {
    data object Welcome : OnboardingStep()
    data object ProviderType : OnboardingStep()
    data object XtreamLogin : OnboardingStep()
    data object M3UUrl : OnboardingStep()
    data object Loading : OnboardingStep()
    data class LiveTvCategories(val categories: List<CategoryEntity>) : OnboardingStep()
    data class SeriesCategories(val categories: List<CategoryEntity>) : OnboardingStep()
    data class VodCategories(val categories: List<CategoryEntity>) : OnboardingStep()
    data object Complete : OnboardingStep()
}

data class OnboardingUiState(
    val currentStep: OnboardingStep = OnboardingStep.Welcome,
    val providerType: ProviderTypeSelection = ProviderTypeSelection.NONE,
    val serverUrl: String = "",
    val username: String = "",
    val password: String = "",
    val m3uUrl: String = "",
    val selectedLiveCategories: Set<String> = emptySet(),
    val selectedSeriesCategories: Set<String> = emptySet(),
    val selectedVodCategories: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val providerId: String? = null
)

enum class ProviderTypeSelection {
    NONE, XTREAM, M3U_URL
}

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val addProviderUseCase: AddProviderUseCase,
    private val syncProviderUseCase: SyncProviderUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun goToStep(step: OnboardingStep) {
        _uiState.update { it.copy(currentStep = step, error = null) }
    }

    fun selectProviderType(type: ProviderTypeSelection) {
        _uiState.update { it.copy(providerType = type) }
    }

    fun updateServerUrl(url: String) {
        _uiState.update { it.copy(serverUrl = url, error = null) }
    }

    fun updateUsername(username: String) {
        _uiState.update { it.copy(username = username, error = null) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    fun updateM3uUrl(url: String) {
        _uiState.update { it.copy(m3uUrl = url, error = null) }
    }

    fun connectProvider(onSuccess: () -> Unit) {
        val state = _uiState.value
        
        val providerType = when (state.providerType) {
            ProviderTypeSelection.XTREAM -> {
                if (state.serverUrl.isBlank() || state.username.isBlank() || state.password.isBlank()) {
                    _uiState.update { it.copy(error = "Bitte alle Felder ausfüllen") }
                    return
                }
                ProviderType.XtreamCodes(
                    serverUrl = normalizeUrl(state.serverUrl),
                    username = state.username,
                    password = state.password
                )
            }
            ProviderTypeSelection.M3U_URL -> {
                if (state.m3uUrl.isBlank()) {
                    _uiState.update { it.copy(error = "Bitte M3U URL eingeben") }
                    return
                }
                ProviderType.M3uUrl(normalizeUrl(state.m3uUrl))
            }
            else -> return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            addProviderUseCase("Mein Provider", providerType)
                .onSuccess { providerId ->
                    _uiState.update { it.copy(providerId = providerId) }
                    
                    // Start sync
                    syncProviderUseCase(providerId).collect { progress ->
                        when (progress) {
                            is SyncProgress.Success -> {
                                _uiState.update { it.copy(isLoading = false) }
                                onSuccess()
                            }
                            is SyncProgress.Error -> {
                                _uiState.update { 
                                    it.copy(isLoading = false, error = progress.message) 
                                }
                            }
                            else -> {}
                        }
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(isLoading = false, error = error.message) 
                    }
                }
        }
    }

    fun toggleLiveCategory(categoryId: String) {
        _uiState.update { state ->
            val current = state.selectedLiveCategories
            val updated = if (current.contains(categoryId)) {
                current - categoryId
            } else {
                current + categoryId
            }
            state.copy(selectedLiveCategories = updated)
        }
    }

    fun toggleSeriesCategory(categoryId: String) {
        _uiState.update { state ->
            val current = state.selectedSeriesCategories
            val updated = if (current.contains(categoryId)) {
                current - categoryId
            } else {
                current + categoryId
            }
            state.copy(selectedSeriesCategories = updated)
        }
    }

    fun toggleVodCategory(categoryId: String) {
        _uiState.update { state ->
            val current = state.selectedVodCategories
            val updated = if (current.contains(categoryId)) {
                current - categoryId
            } else {
                current + categoryId
            }
            state.copy(selectedVodCategories = updated)
        }
    }

    fun selectAllLiveCategories(categories: List<CategoryEntity>) {
        _uiState.update { 
            it.copy(selectedLiveCategories = categories.map { cat -> cat.id }.toSet()) 
        }
    }

    fun deselectAllLiveCategories() {
        _uiState.update { it.copy(selectedLiveCategories = emptySet()) }
    }

    fun selectAllSeriesCategories(categories: List<CategoryEntity>) {
        _uiState.update { 
            it.copy(selectedSeriesCategories = categories.map { cat -> cat.id }.toSet()) 
        }
    }

    fun deselectAllSeriesCategories() {
        _uiState.update { it.copy(selectedSeriesCategories = emptySet()) }
    }

    fun selectAllVodCategories(categories: List<CategoryEntity>) {
        _uiState.update { 
            it.copy(selectedVodCategories = categories.map { cat -> cat.id }.toSet()) 
        }
    }

    fun deselectAllVodCategories() {
        _uiState.update { it.copy(selectedVodCategories = emptySet()) }
    }

    fun saveOnboardingComplete() {
        // Save to preferences that onboarding is complete
        // Also save selected categories for filtering
    }

    private fun normalizeUrl(url: String): String {
        val trimmed = url.trim()
        return when {
            trimmed.startsWith("http://", ignoreCase = true) -> trimmed
            trimmed.startsWith("https://", ignoreCase = true) -> trimmed
            else -> "http://$trimmed"
        }
    }
}
