package com.nextgen.iptv.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.domain.model.ProviderType
import com.nextgen.iptv.domain.usecase.provider.AddProviderUseCase
import com.nextgen.iptv.domain.usecase.provider.SyncProgress
import com.nextgen.iptv.domain.usecase.provider.SyncProviderUseCase
import com.nextgen.iptv.domain.usecase.provider.ValidateProviderUseCase
import com.nextgen.iptv.domain.usecase.provider.ValidationResult
import com.nextgen.iptv.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class OnboardingStep {
    data object Welcome : OnboardingStep()
    data object ProviderType : OnboardingStep()
    data object XtreamLogin : OnboardingStep()
    data object M3UUrl : OnboardingStep()
    data object Validating : OnboardingStep()
    data object Syncing : OnboardingStep()
    data class CategorySelection(val categories: List<CategoryEntity>) : OnboardingStep()
    data object Complete : OnboardingStep()
}

data class OnboardingUiState(
    val currentStep: OnboardingStep = OnboardingStep.Welcome,
    val providerType: ProviderTypeSelection = ProviderTypeSelection.NONE,
    val serverUrl: String = "",
    val username: String = "",
    val password: String = "",
    val m3uUrl: String = "",
    val selectedCategories: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val providerId: String? = null,
    val validationMessage: String? = null,
    val syncMessage: String = "",
    val syncProgress: Int = 0
)

enum class ProviderTypeSelection {
    NONE, XTREAM, M3U_URL
}

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val addProviderUseCase: AddProviderUseCase,
    private val syncProviderUseCase: SyncProviderUseCase,
    private val validateProviderUseCase: ValidateProviderUseCase,
    private val categoryRepository: CategoryRepository
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

    fun connectProvider() {
        val state = _uiState.value
        
        val providerType = when (state.providerType) {
            ProviderTypeSelection.XTREAM -> {
                if (state.serverUrl.isBlank() || state.username.isBlank() || state.password.isBlank()) {
                    _uiState.update { it.copy(error = "Bitte alle Felder ausfullen") }
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

        _uiState.update { it.copy(isLoading = true, error = null, currentStep = OnboardingStep.Validating) }

        viewModelScope.launch {
            // Step 1: Validate connection first (with timeout)
            when (val result = validateProviderUseCase(providerType)) {
                is ValidationResult.Success -> {
                    _uiState.update { 
                        it.copy(
                            validationMessage = result.info,
                            currentStep = OnboardingStep.Syncing,
                            syncMessage = "Provider wird gespeichert...",
                            syncProgress = 5
                        ) 
                    }
                    
                    // Step 2: Add provider
                    addProviderUseCase("Mein Provider", providerType)
                        .onSuccess { providerId ->
                            _uiState.update { 
                                it.copy(
                                    providerId = providerId,
                                    syncMessage = "Synchronisiere Live TV...",
                                    syncProgress = 10
                                ) 
                            }
                            
                            // Step 3: Quick sync ONLY Live TV (fast!)
                            syncProviderUseCase.syncQuick(providerId).collect { progress ->
                                when (progress) {
                                    is SyncProgress.Starting -> {
                                        _uiState.update { 
                                            it.copy(
                                                syncMessage = "Starte Synchronisation...",
                                                syncProgress = 10
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
                                        // Step 4: Load categories for selection
                                        loadCategoriesAndShowSelection(providerId)
                                    }
                                    is SyncProgress.Error -> {
                                        _uiState.update { 
                                            it.copy(
                                                isLoading = false,
                                                error = progress.message,
                                                currentStep = OnboardingStep.XtreamLogin
                                            ) 
                                        }
                                    }
                                }
                            }
                        }
                        .onFailure { error ->
                            _uiState.update { 
                                it.copy(
                                    isLoading = false,
                                    error = "Provider konnte nicht gespeichert werden: ${error.message}",
                                    currentStep = OnboardingStep.XtreamLogin
                                ) 
                            }
                        }
                }
                is ValidationResult.Error -> {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = result.message,
                            currentStep = OnboardingStep.XtreamLogin
                        ) 
                    }
                }
            }
        }
    }
    
    private fun loadCategoriesAndShowSelection(providerId: String) {
        viewModelScope.launch {
            try {
                val categories = categoryRepository.getCategoriesByProvider(providerId).first()
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        currentStep = OnboardingStep.CategorySelection(categories),
                        // Select all by default
                        selectedCategories = categories.map { cat -> cat.id }.toSet()
                    ) 
                }
            } catch (e: Exception) {
                // If loading categories fails, just go to complete
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        currentStep = OnboardingStep.Complete
                    ) 
                }
            }
        }
    }

    fun toggleCategory(categoryId: String) {
        _uiState.update { state ->
            val current = state.selectedCategories
            val updated = if (current.contains(categoryId)) {
                current - categoryId
            } else {
                current + categoryId
            }
            state.copy(selectedCategories = updated)
        }
    }

    fun selectAllCategories(categories: List<CategoryEntity>) {
        _uiState.update { 
            it.copy(selectedCategories = categories.map { cat -> cat.id }.toSet()) 
        }
    }

    fun deselectAllCategories() {
        _uiState.update { it.copy(selectedCategories = emptySet()) }
    }

    fun saveOnboardingComplete() {
        // Save to preferences that onboarding is complete
        // Selected categories are stored for filtering
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
