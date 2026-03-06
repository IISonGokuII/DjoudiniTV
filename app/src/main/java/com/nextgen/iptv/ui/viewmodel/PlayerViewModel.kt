package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class PlayerUiState(
    val streamUrl: String = "",
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class PlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    init {
        val streamUrl: String? = savedStateHandle.get<String>("streamUrl")
        streamUrl?.let { 
            _uiState.update { state -> 
                state.copy(streamUrl = it, isLoading = false) 
            } 
        } ?: run {
            _uiState.update { state -> 
                state.copy(error = "Keine Stream-URL übermittelt", isLoading = false) 
            }
        }
    }
}
