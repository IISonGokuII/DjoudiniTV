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
import com.nextgen.iptv.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategorySelection(
    val liveCategories: List<CategoryEntity> = emptyList(),
    val vodCategories: List<CategoryEntity> = emptyList(),
    val seriesCategories: List<CategoryEntity> = emptyList()
)

sealed class OnboardingStep {
    data object Welcome : OnboardingStep()
    data object ProviderType : OnboardingStep()
    data object XtreamLogin : OnboardingStep()
    data object M3UUrl : OnboardingStep()
    data object Validating : OnboardingStep()
    data object Syncing : OnboardingStep()
    data class CategorySelectionStep(val categories: CategorySelection) : OnboardingStep()
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
    val selectedVodCategories: Set<String> = emptySet(),
    val selectedSeriesCategories: Set<String> = emptySet(),
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
    private val categoryRepository: CategoryRepository,
    private val settingsRepository: SettingsRepository
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
                    
                    addProviderUseCase("Mein Provider", providerType)
                        .onSuccess { providerId ->
                            syncLiveTVAndLoadCategories(providerId)
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
    
    private fun syncLiveTVAndLoadCategories(providerId: String) {
        viewModelScope.launch {
            _uiState.update { 
                it.copy(
                    providerId = providerId,
                    syncMessage = "Synchronisiere Live TV...",
                    syncProgress = 10
                ) 
            }
            
            syncProviderUseCase.syncQuick(providerId).collect { progress ->
                when (progress) {
                    is SyncProgress.Progress -> {
                        _uiState.update { 
                            it.copy(
                                syncMessage = progress.message,
                                syncProgress = 10 + (progress.percent * 0.25).toInt()
                            )
                        }
                    }
                    is SyncProgress.Success -> {
                        loadVodAndSeriesCategories(providerId)
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
                    else -> {}
                }
            }
        }
    }
    
    private fun loadVodAndSeriesCategories(providerId: String) {
        viewModelScope.launch {
            var vodSuccess = false
            var seriesSuccess = false
            var vodError: String? = null
            var seriesError: String? = null
            
            try {
                // Step 1: Sync VOD Categories - WAIT for completion
                _uiState.update { 
                    it.copy(
                        syncMessage = "Lade Film-Kategorien...",
                        syncProgress = 40
                    ) 
                }
                
                syncProviderUseCase.syncVodCategoriesOnly(providerId).collect { progress ->
                    when (progress) {
                        is SyncProgress.Progress -> {
                            _uiState.update { 
                                it.copy(
                                    syncMessage = progress.message,
                                    syncProgress = 40 + (progress.percent * 0.15).toInt()
                                )
                            }
                        }
                        is SyncProgress.Success -> {
                            vodSuccess = true
                        }
                        is SyncProgress.Error -> {
                            vodError = progress.message
                            vodSuccess = true // Continue even if VOD fails
                        }
                        else -> {}
                    }
                }
                
                // Step 2: Sync Series Categories - WAIT for completion
                _uiState.update { 
                    it.copy(
                        syncMessage = "Lade Serien-Kategorien...",
                        syncProgress = 60
                    ) 
                }
                
                syncProviderUseCase.syncSeriesCategoriesOnly(providerId).collect { progress ->
                    when (progress) {
                        is SyncProgress.Progress -> {
                            _uiState.update { 
                                it.copy(
                                    syncMessage = progress.message,
                                    syncProgress = 60 + (progress.percent * 0.15).toInt()
                                )
                            }
                        }
                        is SyncProgress.Success -> {
                            seriesSuccess = true
                        }
                        is SyncProgress.Error -> {
                            seriesError = progress.message
                            seriesSuccess = true // Continue even if Series fails
                        }
                        else -> {}
                    }
                }
                
                // Step 3: Only show category selection when BOTH are done
                if (vodSuccess && seriesSuccess) {
                    showCategorySelection(providerId)
                }
                
            } catch (e: Exception) {
                // On exception, still try to show what we have
                showCategorySelection(providerId)
            }
        }
    }
    
    private fun showCategorySelection(providerId: String) {
        viewModelScope.launch {
            try {
                val allCategories = categoryRepository.getCategoriesByProvider(providerId).first()
                
                val liveCats = allCategories.filter { it.type == "live" }
                val vodCats = allCategories.filter { it.type == "vod" }
                val seriesCats = allCategories.filter { it.type == "series" }
                
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        currentStep = OnboardingStep.CategorySelectionStep(
                            CategorySelection(
                                liveCategories = liveCats,
                                vodCategories = vodCats,
                                seriesCategories = seriesCats
                            )
                        ),
                        // Default: ALL categories selected (user can deselect)
                        selectedLiveCategories = liveCats.map { cat -> cat.id }.toSet(),
                        selectedVodCategories = vodCats.map { cat -> cat.id }.toSet(),
                        selectedSeriesCategories = seriesCats.map { cat -> cat.id }.toSet()
                    ) 
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Fehler beim Laden der Kategorien: ${e.message}",
                        currentStep = OnboardingStep.Complete
                    ) 
                }
            }
        }
    }

    fun toggleLiveCategory(categoryId: String) {
        _uiState.update { state ->
            val current = state.selectedLiveCategories
            val updated = if (current.contains(categoryId)) current - categoryId else current + categoryId
            state.copy(selectedLiveCategories = updated)
        }
    }

    fun toggleVodCategory(categoryId: String) {
        _uiState.update { state ->
            val current = state.selectedVodCategories
            val updated = if (current.contains(categoryId)) current - categoryId else current + categoryId
            state.copy(selectedVodCategories = updated)
        }
    }

    fun toggleSeriesCategory(categoryId: String) {
        _uiState.update { state ->
            val current = state.selectedSeriesCategories
            val updated = if (current.contains(categoryId)) current - categoryId else current + categoryId
            state.copy(selectedSeriesCategories = updated)
        }
    }

    fun selectAllLiveCategories(categories: List<CategoryEntity>) {
        _uiState.update { it.copy(selectedLiveCategories = categories.map { cat -> cat.id }.toSet()) }
    }

    fun deselectAllLiveCategories() {
        _uiState.update { it.copy(selectedLiveCategories = emptySet()) }
    }

    fun selectAllVodCategories(categories: List<CategoryEntity>) {
        _uiState.update { it.copy(selectedVodCategories = categories.map { cat -> cat.id }.toSet()) }
    }

    fun deselectAllVodCategories() {
        _uiState.update { it.copy(selectedVodCategories = emptySet()) }
    }

    fun selectAllSeriesCategories(categories: List<CategoryEntity>) {
        _uiState.update { it.copy(selectedSeriesCategories = categories.map { cat -> cat.id }.toSet()) }
    }

    fun deselectAllSeriesCategories() {
        _uiState.update { it.copy(selectedSeriesCategories = emptySet()) }
    }

    /**
     * Saves selected categories and returns success status
     * Call this with await() from UI to ensure completion before navigating
     */
    suspend fun saveOnboardingComplete(): Boolean {
        val state = _uiState.value
        val providerId = state.providerId ?: return false
        
        return try {
            // Save selected categories to settings
            settingsRepository.setSelectedLiveCategories(providerId, state.selectedLiveCategories)
            settingsRepository.setSelectedVodCategories(providerId, state.selectedVodCategories)
            settingsRepository.setSelectedSeriesCategories(providerId, state.selectedSeriesCategories)
            true
        } catch (e: Exception) {
            false
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
}
