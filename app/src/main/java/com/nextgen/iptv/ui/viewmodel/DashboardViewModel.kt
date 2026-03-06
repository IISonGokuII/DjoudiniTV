package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.VodProgressEntity
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.StreamRepository
import com.nextgen.iptv.domain.repository.VodProgressRepository
import com.nextgen.iptv.domain.usecase.provider.GetProvidersWithStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val providers: List<ProviderStatus> = emptyList(),
    val continueWatching: List<MovieWithProgress> = emptyList(),
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
    private val getProvidersWithStatusUseCase: GetProvidersWithStatusUseCase,
    private val vodProgressRepository: VodProgressRepository,
    private val streamRepository: StreamRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState(isLoading = true))
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                combine(
                    getProvidersWithStatusUseCase(),
                    vodProgressRepository.getAllProgress()
                ) { providers, progressList ->
                    // Filter progress for continue watching (5-95%)
                    val continueWatchingProgress = progressList
                        .filter { it.progressMs > 0 && it.durationMs > 0 }
                        .map { progress ->
                            val percent = ((progress.progressMs * 100) / progress.durationMs).toInt()
                            progress to percent
                        }
                        .filter { (_, percent) -> percent in 5..95 }
                        .sortedByDescending { (progress, _) -> progress.lastWatched }
                        .take(10) // Top 10 recently watched
                    
                    // Load movie details for each progress entry
                    val continueWatching = continueWatchingProgress.mapNotNull { (progress, percent) ->
                        // Find stream by generating URL hash
                        val allStreams = streamRepository.getAllStreams().first()
                        val stream = allStreams.find { 
                            VodViewModel.generateStreamId(it.streamUrl) == progress.streamId 
                        }
                        stream?.let {
                            MovieWithProgress(
                                movie = it,
                                progressMs = progress.progressMs,
                                durationMs = progress.durationMs,
                                progressPercent = percent
                            )
                        }
                    }
                    
                    providers to continueWatching
                }.collect { (providers, continueWatching) ->
                    _uiState.update { 
                        it.copy(
                            providers = providers,
                            continueWatching = continueWatching,
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

    fun loadProviders() {
        loadData()
    }

    fun deleteProvider(providerId: String) {
        viewModelScope.launch {
            try {
                providerRepository.deleteProvider(providerId)
                loadData()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
