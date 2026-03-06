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
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
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
    data class SelectLiveTvCategories(val categories: List<CategoryEntity>, val selectedIds: Set<String>) : OnboardingStep()
    data class SelectSeriesCategories(val categories: List<CategoryEntity>, val selectedIds: Set<String>) : OnboardingStep()
    data class SelectVodCategories(val categories: List<CategoryEntity>, val selectedIds: Set<String>) : OnboardingStep()
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
    val tempLiveSelection: Set<String> = emptySet(),
    val tempSeriesSelection: Set<String> = emptySet(),
    val tempVodSelection: Set<String> = emptySet()
)

enum class ProviderTypeSelection { NONE, XTREAM, M3U_URL }

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
    private var currentSyncJob: Job? = null

    fun goToStep(step: OnboardingStep) { _uiState.update { it.copy(currentStep = step, error = null) } }
    fun selectProviderType(type: ProviderTypeSelection) { _uiState.update { it.copy(providerType = type) } }
    fun updateServerUrl(url: String) { _uiState.update { it.copy(serverUrl = url, error = null) } }
    fun updateUsername(username: String) { _uiState.update { it.copy(username = username, error = null) } }
    fun updatePassword(password: String) { _uiState.update { it.copy(password = password, error = null) } }
    fun updateM3uUrl(url: String) { _uiState.update { it.copy(m3uUrl = url, error = null) } }

    fun connectProvider() {
        currentSyncJob?.cancel()
        val state = _uiState.value
        val providerType = when (state.providerType) {
            ProviderTypeSelection.XTREAM -> {
                if (state.serverUrl.isBlank() || state.username.isBlank() || state.password.isBlank()) {
                    _uiState.update { it.copy(error = "Bitte alle Felder ausfullen") }
                    return
                }
                ProviderType.XtreamCodes(serverUrl = normalizeUrl(state.serverUrl), username = state.username, password = state.password)
            }
            ProviderTypeSelection.M3U_URL -> {
                if (state.m3uUrl.isBlank()) { _uiState.update { it.copy(error = "Bitte M3U URL eingeben") }; return }
                ProviderType.M3uUrl(normalizeUrl(state.m3uUrl))
            }
            else -> return
        }
        _uiState.update { it.copy(isLoading = true, error = null, currentStep = OnboardingStep.Validating) }
        currentSyncJob = viewModelScope.launch {
            try {
                when (val result = validateProviderUseCase(providerType)) {
                    is ValidationResult.Success -> {
                        _uiState.update { it.copy(validationMessage = result.info, currentStep = OnboardingStep.Syncing, syncMessage = "Provider wird gespeichert...", syncProgress = 5) }
                        addProviderUseCase("Mein Provider", providerType)
                            .onSuccess { providerId -> _uiState.update { it.copy(providerId = providerId) }; startSequentialSync(providerId) }
                            .onFailure { error -> _uiState.update { it.copy(isLoading = false, error = "Provider konnte nicht gespeichert werden: ${error.message}", currentStep = OnboardingStep.XtreamLogin) } }
                    }
                    is ValidationResult.Error -> { _uiState.update { it.copy(isLoading = false, error = result.message, currentStep = OnboardingStep.XtreamLogin) } }
                }
            } catch (e: CancellationException) { _uiState.update { it.copy(isLoading = false, error = "Vorgang abgebrochen", currentStep = OnboardingStep.XtreamLogin) } }
        }
    }

    private fun startSequentialSync(providerId: String) {
        currentSyncJob?.cancel()
        currentSyncJob = viewModelScope.launch {
            _uiState.update { it.copy(syncMessage = "Synchronisiere Live TV...", syncProgress = 10) }
            try {
                syncProviderUseCase.syncQuick(providerId).collect { progress ->
                    when (progress) {
                        is SyncProgress.Progress -> { _uiState.update { it.copy(syncMessage = progress.message, syncProgress = 10 + (progress.percent * 0.3).toInt()) } }
                        is SyncProgress.Success -> { loadLiveTvCategoriesForSelection(providerId) }
                        is SyncProgress.Error -> { _uiState.update { it.copy(isLoading = false, error = "Live TV Sync fehlgeschlagen: ${progress.message}", currentStep = OnboardingStep.XtreamLogin) } }
                        else -> {}
                    }
                }
            } catch (e: CancellationException) { _uiState.update { it.copy(isLoading = false, error = "Synchronisation abgebrochen", currentStep = OnboardingStep.XtreamLogin) } }
        }
    }

    private fun loadLiveTvCategoriesForSelection(providerId: String) {
        currentSyncJob = viewModelScope.launch {
            try {
                _uiState.update { it.copy(syncMessage = "Lade Live TV Kategorien...", syncProgress = 40) }
                val allCategories = categoryRepository.getCategoriesByProvider(providerId).first()
                val liveCats = allCategories.filter { it.type == "live" }
                if (liveCats.isEmpty()) { _uiState.update { it.copy(isLoading = false, tempLiveSelection = emptySet()) }; startSeriesCategorySync(providerId); return@launch }
                val selectedIds = liveCats.map { cat -> cat.id }.toSet()
                _uiState.update { it.copy(isLoading = false, currentStep = OnboardingStep.SelectLiveTvCategories(categories = liveCats.toList(), selectedIds = selectedIds), tempLiveSelection = selectedIds) }
            } catch (e: Exception) { if (e !is CancellationException) _uiState.update { it.copy(isLoading = false, error = "Fehler beim Laden: ${e.message}", currentStep = OnboardingStep.XtreamLogin) } }
        }
    }

    fun updateLiveTvSelection(selectedIds: Set<String>) {
        _uiState.update { currentState ->
            val currentStep = currentState.currentStep
            if (currentStep is OnboardingStep.SelectLiveTvCategories) currentState.copy(tempLiveSelection = selectedIds, currentStep = currentStep.copy(selectedIds = selectedIds)) else currentState
        }
    }

    fun confirmLiveTvSelectionAndContinue() {
        currentSyncJob?.cancel()
        currentSyncJob = viewModelScope.launch {
            try {
                val state = _uiState.value
                val providerId = state.providerId ?: run { _uiState.update { it.copy(error = "Provider ID fehlt", isLoading = false) }; return@launch }
                settingsRepository.setSelectedLiveCategories(providerId, state.tempLiveSelection)
                startSeriesCategorySync(providerId)
            } catch (e: Exception) { if (e !is CancellationException) _uiState.update { it.copy(isLoading = false, error = "Fehler: ${e.message}") } }
        }
    }

    private suspend fun startSeriesCategorySync(providerId: String) {
        _uiState.update { it.copy(isLoading = true, syncMessage = "Lade Serien-Kategorien...", syncProgress = 45) }
        try {
            syncProviderUseCase.syncSeriesCategoriesOnly(providerId).collect { progress ->
                when (progress) {
                    is SyncProgress.Progress -> { _uiState.update { it.copy(syncMessage = progress.message, syncProgress = 45 + (progress.percent * 0.1).toInt()) } }
                    is SyncProgress.Success -> { loadSeriesCategoriesForSelection(providerId) }
                    is SyncProgress.Error -> { loadSeriesCategoriesForSelection(providerId) }
                    else -> {}
                }
            }
        } catch (e: Exception) { if (e !is CancellationException) loadSeriesCategoriesForSelection(providerId) }
    }

    private fun loadSeriesCategoriesForSelection(providerId: String) {
        currentSyncJob = viewModelScope.launch {
            try {
                val allCategories = categoryRepository.getCategoriesByProvider(providerId).first()
                val seriesCats = allCategories.filter { it.type == "series" }
                val selectedIds = if (seriesCats.isNotEmpty()) seriesCats.map { cat -> cat.id }.toSet() else emptySet()
                _uiState.update { it.copy(isLoading = false, currentStep = OnboardingStep.SelectSeriesCategories(categories = seriesCats.toList(), selectedIds = selectedIds), tempSeriesSelection = selectedIds) }
            } catch (e: Exception) { if (e !is CancellationException) _uiState.update { it.copy(isLoading = false, error = "Fehler: ${e.message}") } }
        }
    }

    fun updateSeriesSelection(selectedIds: Set<String>) {
        _uiState.update { currentState ->
            val currentStep = currentState.currentStep
            if (currentStep is OnboardingStep.SelectSeriesCategories) currentState.copy(tempSeriesSelection = selectedIds, currentStep = currentStep.copy(selectedIds = selectedIds)) else currentState
        }
    }

    fun confirmSeriesSelectionAndContinue() {
        currentSyncJob?.cancel()
        currentSyncJob = viewModelScope.launch {
            try {
                val state = _uiState.value
                val providerId = state.providerId ?: run { _uiState.update { it.copy(error = "Provider ID fehlt", isLoading = false) }; return@launch }
                settingsRepository.setSelectedSeriesCategories(providerId, state.tempSeriesSelection)
                // KEY FIX: Skip full series sync during onboarding - it's too slow!
                _uiState.update { it.copy(isLoading = true, syncMessage = "Lade Film-Kategorien...", syncProgress = 75) }
                startVodCategorySync(providerId)
            } catch (e: Exception) { if (e !is CancellationException) _uiState.update { it.copy(isLoading = false, error = "Fehler: ${e.message}") } }
        }
    }

    private suspend fun startVodCategorySync(providerId: String) {
        try {
            syncProviderUseCase.syncVodCategoriesOnly(providerId).collect { progress ->
                when (progress) {
                    is SyncProgress.Progress -> { _uiState.update { it.copy(syncMessage = progress.message, syncProgress = 75 + (progress.percent * 0.1).toInt()) } }
                    is SyncProgress.Success -> { loadVodCategoriesForSelection(providerId) }
                    is SyncProgress.Error -> { loadVodCategoriesForSelection(providerId) }
                    else -> {}
                }
            }
        } catch (e: Exception) { if (e !is CancellationException) loadVodCategoriesForSelection(providerId) }
    }

    private fun loadVodCategoriesForSelection(providerId: String) {
        currentSyncJob = viewModelScope.launch {
            try {
                val allCategories = categoryRepository.getCategoriesByProvider(providerId).first()
                val vodCats = allCategories.filter { it.type == "vod" }
                val selectedIds = if (vodCats.isNotEmpty()) vodCats.map { cat -> cat.id }.toSet() else emptySet()
                _uiState.update { it.copy(isLoading = false, currentStep = OnboardingStep.SelectVodCategories(categories = vodCats.toList(), selectedIds = selectedIds), tempVodSelection = selectedIds) }
            } catch (e: Exception) { if (e !is CancellationException) _uiState.update { it.copy(isLoading = false, error = "Fehler: ${e.message}") } }
        }
    }

    fun updateVodSelection(selectedIds: Set<String>) {
        _uiState.update { currentState ->
            val currentStep = currentState.currentStep
            if (currentStep is OnboardingStep.SelectVodCategories) currentState.copy(tempVodSelection = selectedIds, currentStep = currentStep.copy(selectedIds = selectedIds)) else currentState
        }
    }

    fun confirmVodSelectionAndFinish(onComplete: () -> Unit) {
        currentSyncJob?.cancel()
        currentSyncJob = viewModelScope.launch {
            try {
                val state = _uiState.value
                val providerId = state.providerId ?: run { _uiState.update { it.copy(error = "Provider ID fehlt", isLoading = false) }; return@launch }
                settingsRepository.setSelectedVodCategories(providerId, state.tempVodSelection)
                // KEY FIX: Skip full VOD sync during onboarding - it's too slow!
                _uiState.update { it.copy(isLoading = false, currentStep = OnboardingStep.Complete, syncProgress = 100, syncMessage = "Fertig!") }
                onComplete()
            } catch (e: Exception) { if (e !is CancellationException) _uiState.update { it.copy(isLoading = false, error = "Fehler: ${e.message}") } }
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

    override fun onCleared() { super.onCleared(); currentSyncJob?.cancel() }
}
