package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.domain.usecase.progress.GetVodProgressUseCase
import com.nextgen.iptv.domain.usecase.progress.SaveVodProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlayerUiState(
    val streamUrl: String = "",
    val streamId: String = "",
    val savedProgressMs: Long = 0,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class PlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getVodProgressUseCase: GetVodProgressUseCase,
    private val saveVodProgressUseCase: SaveVodProgressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    init {
        val streamUrl: String? = savedStateHandle.get<String>("streamUrl")
        streamUrl?.let { url ->
            // Generate a stable stream ID from the URL
            val streamId = generateStreamId(url)
            _uiState.update { state -> 
                state.copy(streamUrl = url, streamId = streamId, isLoading = false) 
            }
            // Load saved progress for VOD
            loadSavedProgress(streamId)
        } ?: run {
            _uiState.update { state -> 
                state.copy(error = "Keine Stream-URL übermittelt", isLoading = false) 
            }
        }
    }

    private fun generateStreamId(url: String): String {
        // Use URL hashCode as a simple unique ID, or use the last part of the path
        return url.hashCode().toString()
    }

    private fun loadSavedProgress(streamId: String) {
        viewModelScope.launch {
            try {
                val savedProgress = getVodProgressUseCase.getProgressMs(streamId)
                savedProgress?.let { progressMs ->
                    _uiState.update { state ->
                        state.copy(savedProgressMs = progressMs)
                    }
                }
            } catch (e: Exception) {
                // Silently fail - no saved progress is not an error
            }
        }
    }

    fun saveProgress(currentPositionMs: Long, durationMs: Long) {
        val state = _uiState.value
        if (state.streamId.isNotBlank() && durationMs > 0) {
            viewModelScope.launch {
                try {
                    // Only save if progress is between 5% and 95%
                    val progressPercent = (currentPositionMs * 100 / durationMs)
                    if (progressPercent in 5..95) {
                        saveVodProgressUseCase(
                            streamId = state.streamId,
                            progressMs = currentPositionMs,
                            durationMs = durationMs
                        )
                    }
                } catch (e: Exception) {
                    // Silently fail - saving progress is not critical
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
