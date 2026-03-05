package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.domain.repository.StreamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlayerUiState(
    val stream: StreamEntity? = null,
    val streamUrl: String = "",
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val streamRepository: StreamRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    init {
        val streamId: String? = savedStateHandle.get<String>("streamId")
        streamId?.let { loadStream(it) }
    }

    private fun loadStream(streamId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                streamRepository.getStreamById(streamId)?.let { stream ->
                    _uiState.update {
                        it.copy(
                            stream = stream,
                            streamUrl = stream.streamUrl,
                            isLoading = false
                        )
                    }
                } ?: run {
                    _uiState.update {
                        it.copy(
                            error = "Stream nicht gefunden",
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }
}
