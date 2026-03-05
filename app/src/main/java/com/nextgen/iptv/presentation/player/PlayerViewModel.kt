package com.nextgen.iptv.presentation.player

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.nextgen.iptv.data.local.entity.EpgEventEntity
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.data.player.ExoPlayerManager
import com.nextgen.iptv.domain.repository.StreamRepository
import com.nextgen.iptv.domain.usecase.epg.GetCurrentEpgUseCase
import com.nextgen.iptv.domain.usecase.epg.GetUpcomingEpgUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class PlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val streamRepository: StreamRepository,
    private val getCurrentEpgUseCase: GetCurrentEpgUseCase,
    private val getUpcomingEpgUseCase: GetUpcomingEpgUseCase,
    private val exoPlayerManager: ExoPlayerManager
) : ViewModel() {
    
    val exoPlayer: ExoPlayer = exoPlayerManager.initializePlayer()
    
    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()
    
    private var positionUpdateJob: Job? = null
    private var epgUpdateJob: Job? = null
    
    private val streamId: String = savedStateHandle.get<String>("streamId") ?: ""
    
    init {
        loadStream()
        startPositionUpdates()
    }
    
    private fun loadStream() {
        viewModelScope.launch {
            val stream = streamRepository.getStreamById(streamId)
            _uiState.update { it.copy(stream = stream) }
            
            stream?.let {
                playStream(it.streamUrl)
                loadEpg(it.epgChannelId)
            }
        }
    }
    
    private fun playStream(url: String) {
        exoPlayerManager.playStream(url)
    }
    
    private fun loadEpg(epgChannelId: String?) {
        if (epgChannelId == null) return
        
        viewModelScope.launch {
            val current = getCurrentEpgUseCase(epgChannelId)
            val upcoming = getUpcomingEpgUseCase(epgChannelId)
            
            _uiState.update {
                it.copy(
                    currentEpg = current,
                    upcomingEpg = upcoming
                )
            }
        }
        
        // Periodic EPG refresh
        epgUpdateJob?.cancel()
        epgUpdateJob = viewModelScope.launch {
            while (isActive) {
                delay(60000) // Refresh every minute
                val current = getCurrentEpgUseCase(epgChannelId)
                val upcoming = getUpcomingEpgUseCase(epgChannelId)
                _uiState.update {
                    it.copy(
                        currentEpg = current,
                        upcomingEpg = upcoming
                    )
                }
            }
        }
    }
    
    private fun startPositionUpdates() {
        positionUpdateJob?.cancel()
        positionUpdateJob = viewModelScope.launch {
            while (isActive) {
                exoPlayerManager.updatePosition()
                _uiState.update {
                    it.copy(
                        currentPosition = exoPlayerManager.currentPosition.value,
                        duration = exoPlayerManager.duration.value
                    )
                }
                delay(1000)
            }
        }
    }
    
    fun play() = exoPlayerManager.play()
    fun pause() = exoPlayerManager.pause()
    fun seekTo(positionMs: Long) = exoPlayerManager.seekTo(positionMs)
    
    override fun onCleared() {
        super.onCleared()
        positionUpdateJob?.cancel()
        epgUpdateJob?.cancel()
        exoPlayerManager.release()
    }
}

data class PlayerUiState(
    val stream: StreamEntity? = null,
    val currentEpg: EpgEventEntity? = null,
    val upcomingEpg: List<EpgEventEntity> = emptyList(),
    val currentPosition: Long = 0L,
    val duration: Long = 0L
)
