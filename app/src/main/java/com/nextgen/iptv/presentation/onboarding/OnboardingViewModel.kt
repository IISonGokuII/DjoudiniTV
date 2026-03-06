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

// Sequential category selection data
sealed class CategorySelectStep {
    abstract val categories: List<CategoryEntity>
    abstract val selectedIds: Set<String>
    
    data class LiveTvStep(
        override val categories: List<CategoryEntity>,
        override val selectedIds: Set<String>
    ) : CategorySelectStep()
    
    data class SeriesStep(
        override val categories: List<CategoryEntity>,
        override val selectedIds: Set<String>
    ) : CategorySelectStep()
    
    data class VodStep(
        override val categories: List<CategoryEntity>,
        override val selectedIds: Set<String>
    ) : CategorySelectStep()
}

sealed class OnboardingStep {
    data object Welcome : OnboardingStep()
    data object ProviderType : OnboardingStep()
    data object XtreamLogin : OnboardingStep()
    data object M3UUrl : OnboardingStep()
    data object Validating : OnboardingStep()
    data object Syncing : OnboardingStep()
    
    // Sequential category selection steps
    data class SelectLiveTvCategories(
        val categories: List<CategoryEntity>,
        val selectedIds: Set<String>
    ) : OnboardingStep()
    
    data class SelectSeriesCategories(
        val categories: List<CategoryEntity>,
        val selectedIds: Set<String>
    ) : OnboardingStep()
    
    data class SelectVodCategories(
        val categories: List<CategoryEntity>,
        val selectedIds: Set<String>
    ) : OnboardingStep()
    
    data object Complete : OnboardingStep()
}

data class OnboardingUiState(
    val currentStep: OnboardingStep = OnboardingStep.Welcome,
    val providerType: ProviderTypeSelection = ProviderTypeSelection.NONE,
    val serverUrl: String = "",
    val username: String = "",
    val password: String = "",
    val m3uUrl: String = "",
    val providerId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val validationMessage: String? = null,
    val syncMessage: String = "",
    val syncProgress: Int = 0,
    // Temporarily store selections during flow
    val tempLiveSelection: Set<String> = emptySet(),
    val tempSeriesSelection: Set<String> = emptySet(),
    val tempVodSelection: Set<String> = emptySet()
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
                            _uiState.update { it.copy(providerId = providerId) }
                            startSequentialSync(providerId)
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
    
    /**
     * STEP 1: Sync Live TV and show category selection
     */
    private fun startSequentialSync(providerId: String) {
        viewModelScope.launch {
            _uiState.update { 
                it.copy(
                    syncMessage = "Synchronisiere Live TV...",
                    syncProgress = 10
                ) 
            }
            
            // Step 1: Sync Live TV (categories + streams)
            syncProviderUseCase.syncQuick(providerId).collect { progress ->
                when (progress) {
                    is SyncProgress.Progress -> {
                        _uiState.update { 
                            it.copy(
                                syncMessage = progress.message,
                                syncProgress = 10 + (progress.percent * 0.3).toInt()
                            )
                        }
                    }
                    is SyncProgress.Success -> {
                        // Live TV done, now load Live TV categories for selection
                        loadLiveTvCategoriesForSelection(providerId)
                    }
                    is SyncProgress.Error -> {
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                error = "Live TV Sync fehlgeschlagen: ${progress.message}",
                                currentStep = OnboardingStep.XtreamLogin
                            ) 
                        }
                    }
                    else -> {}
                }
            }
        }
    }
    
    private fun loadLiveTvCategoriesForSelection(providerId: String) {
        viewModelScope.launch {
            try {
                val allCategories = categoryRepository.getCategoriesByProvider(providerId).first()
                val liveCats = allCategories.filter { it.type == "live" }
                
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        currentStep = OnboardingStep.SelectLiveTvCategories(
                            categories = liveCats,
                            selectedIds = liveCats.map { cat -> cat.id }.toSet() // All selected by default
                        ),
                        tempLiveSelection = liveCats.map { cat -> cat.id }.toSet()
                    ) 
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Fehler beim Laden der Live TV Kategorien: ${e.message}",
                        currentStep = OnboardingStep.XtreamLogin
                    ) 
                }
            }
        }
    }
    
    fun updateLiveTvSelection(selectedIds: Set<String>) {
        _uiState.update { 
            it.copy(
                tempLiveSelection = selectedIds,
                currentStep = (it.currentStep as? OnboardingStep.SelectLiveTvCategories)?.let { step ->
                    step.copy(selectedIds = selectedIds)
                } ?: it.currentStep
            ) 
        }
    }
    
    /**
     * After Live TV selection, save and continue to Series
     */
    fun confirmLiveTvSelectionAndContinue() {
        viewModelScope.launch {
            val state = _uiState.value
            val providerId = state.providerId ?: return@launch
            
            // Save Live TV selection
            settingsRepository.setSelectedLiveCategories(providerId, state.tempLiveSelection)
            
            // Move to Series sync
            _uiState.update { 
                it.copy(
                    isLoading = true,
                    syncMessage = "Lade Serien-Kategorien...",
                    syncProgress = 45
                ) 
            }
            
            // Sync Series categories
            syncProviderUseCase.syncSeriesCategoriesOnly(providerId).collect { progress ->
                when (progress) {
                    is SyncProgress.Progress -> {
                        _uiState.update { 
                            it.copy(
                                syncMessage = progress.message,
                                syncProgress = 45 + (progress.percent * 0.1).toInt()
                            )
                        }
                    }
                    is SyncProgress.Success -> {
                        loadSeriesCategoriesForSelection(providerId)
                    }
                    is SyncProgress.Error -> {
                        // Continue even if series fails - maybe user doesn't want series
                        loadSeriesCategoriesForSelection(providerId)
                    }
                    else -> {}
                }
            }
        }
    }
    
    private fun loadSeriesCategoriesForSelection(providerId: String) {
        viewModelScope.launch {
            val allCategories = categoryRepository.getCategoriesByProvider(providerId).first()
            val seriesCats = allCategories.filter { it.type == "series" }
            
            _uiState.update { 
                it.copy(
                    isLoading = false,
                    currentStep = OnboardingStep.SelectSeriesCategories(
                        categories = seriesCats,
                        selectedIds = seriesCats.map { cat -> cat.id }.toSet() // All selected by default
                    ),
                    tempSeriesSelection = seriesCats.map { cat -> cat.id }.toSet()
                ) 
            }
        }
    }
    
    fun updateSeriesSelection(selectedIds: Set<String>) {
        _uiState.update { 
            it.copy(
                tempSeriesSelection = selectedIds,
                currentStep = (it.currentStep as? OnboardingStep.SelectSeriesCategories)?.let { step ->
                    step.copy(selectedIds = selectedIds)
                } ?: it.currentStep
            ) 
        }
    }
    
    /**
     * After Series selection, save and sync Series, then continue to VOD
     */
    fun confirmSeriesSelectionAndContinue() {
        viewModelScope.launch {
            val state = _uiState.value
            val providerId = state.providerId ?: return@launch
            
            // Save Series selection
            settingsRepository.setSelectedSeriesCategories(providerId, state.tempSeriesSelection)
            
            // If user selected series categories, sync the actual series
            if (state.tempSeriesSelection.isNotEmpty()) {
                _uiState.update { 
                    it.copy(
                        isLoading = true,
                        syncMessage = "Synchronisiere Serien...",
                        syncProgress = 60
                    ) 
                }
                
                syncProviderUseCase(providerId).collect { progress ->
                    when (progress) {
                        is SyncProgress.Progress -> {
                            _uiState.update { 
                                it.copy(
                                    syncMessage = progress.message,
                                    syncProgress = 60 + (progress.percent * 0.1).toInt()
                                )
                            }
                        }
                        is SyncProgress.Success, is SyncProgress.Error -> {
                            // Move to VOD regardless of success
                            startVodCategorySync(providerId)
                        }
                        else -> {}
                    }
                }
            } else {
                // No series selected, skip to VOD
                startVodCategorySync(providerId)
            }
        }
    }
    
    private suspend fun startVodCategorySync(providerId: String) {
        _uiState.update { 
            it.copy(
                isLoading = true,
                syncMessage = "Lade Film-Kategorien...",
                syncProgress = 75
            ) 
        }
        
        syncProviderUseCase.syncVodCategoriesOnly(providerId).collect { progress ->
            when (progress) {
                is SyncProgress.Progress -> {
                    _uiState.update { 
                        it.copy(
                            syncMessage = progress.message,
                            syncProgress = 75 + (progress.percent * 0.1).toInt()
                        )
                    }
                }
                is SyncProgress.Success -> {
                    loadVodCategoriesForSelection(providerId)
                }
                is SyncProgress.Error -> {
                    loadVodCategoriesForSelection(providerId)
                }
                else -> {}
            }
        }
    }
    
    private fun loadVodCategoriesForSelection(providerId: String) {
        viewModelScope.launch {
            val allCategories = categoryRepository.getCategoriesByProvider(providerId).first()
            val vodCats = allCategories.filter { it.type == "vod" }
            
            _uiState.update { 
                it.copy(
                    isLoading = false,
                    currentStep = OnboardingStep.SelectVodCategories(
                        categories = vodCats,
                        selectedIds = vodCats.map { cat -> cat.id }.toSet() // All selected by default
                    ),
                    tempVodSelection = vodCats.map { cat -> cat.id }.toSet()
                ) 
            }
        }
    }
    
    fun updateVodSelection(selectedIds: Set<String>) {
        _uiState.update { 
            it.copy(
                tempVodSelection = selectedIds,
                currentStep = (it.currentStep as? OnboardingStep.SelectVodCategories)?.let { step ->
                    step.copy(selectedIds = selectedIds)
                } ?: it.currentStep
            ) 
        }
    }
    
    /**
     * Final step: Save VOD selection, sync VOD, finish
     */
    fun confirmVodSelectionAndFinish(onComplete: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            val providerId = state.providerId ?: return@launch
            
            // Save VOD selection
            settingsRepository.setSelectedVodCategories(providerId, state.tempVodSelection)
            
            // If user selected VOD categories, sync the actual movies
            if (state.tempVodSelection.isNotEmpty()) {
                _uiState.update { 
                    it.copy(
                        isLoading = true,
                        syncMessage = "Synchronisiere Filme...",
                        syncProgress = 90
                    ) 
                }
                
                // Note: Full sync includes VOD - or we need a VOD-only sync method
                // For now, mark as complete - VOD will sync on-demand or via settings
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        currentStep = OnboardingStep.Complete
                    ) 
                }
            } else {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        currentStep = OnboardingStep.Complete
                    ) 
                }
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
}
