package com.nextgen.iptv.data.player

import android.content.Context
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@UnstableApi
class ExoPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val okHttpClient: OkHttpClient
) {
    private var exoPlayer: ExoPlayer? = null
    
    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.Idle)
    val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()
    
    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()
    
    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()
    
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()
    
    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            _playerState.value = when (playbackState) {
                Player.STATE_IDLE -> PlayerState.Idle
                Player.STATE_BUFFERING -> PlayerState.Buffering
                Player.STATE_READY -> PlayerState.Ready
                Player.STATE_ENDED -> PlayerState.Ended
                else -> PlayerState.Idle
            }
        }
        
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
        }
        
        override fun onPlayerError(error: PlaybackException) {
            _playerState.value = PlayerState.Error(error.message ?: "Unknown error")
        }
    }
    
    fun initializePlayer(): ExoPlayer {
        if (exoPlayer != null) {
            return exoPlayer!!
        }
        
        // Optimized load control for fast channel zapping
        val loadControl = DefaultLoadControl.Builder()
            .setBufferDurationsMs(
                1000,   // minBufferMs - 1s for fast zapping
                10000,  // maxBufferMs - 10s max
                500,    // bufferForPlaybackMs
                1000    // bufferForPlaybackAfterRebufferMs
            )
            .build()
        
        val trackSelector = DefaultTrackSelector(context).apply {
            parameters = buildUponParameters()
                .setPreferredVideoMimeType("video/avc")
                .build()
        }
        
        exoPlayer = ExoPlayer.Builder(context)
            .setLoadControl(loadControl)
            .setTrackSelector(trackSelector)
            .build()
            .apply {
                addListener(playerListener)
                playWhenReady = true
            }
        
        return exoPlayer!!
    }
    
    fun playStream(url: String, headers: Map<String, String> = emptyMap()) {
        val player = exoPlayer ?: initializePlayer()
        
        val dataSourceFactory = OkHttpDataSource.Factory(okHttpClient)
            .setDefaultRequestProperties(headers)
        
        val mediaItem = MediaItem.fromUri(url)
        
        // Detect if HLS or progressive
        val mediaSource = if (url.contains(".m3u8") || url.contains("/live/")) {
            HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem)
        } else {
            androidx.media3.exoplayer.source.ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem)
        }
        
        player.setMediaSource(mediaSource)
        player.prepare()
    }
    
    fun play() {
        exoPlayer?.play()
    }
    
    fun pause() {
        exoPlayer?.pause()
    }
    
    fun stop() {
        exoPlayer?.stop()
    }
    
    fun seekTo(positionMs: Long) {
        exoPlayer?.seekTo(positionMs)
    }
    
    fun release() {
        exoPlayer?.removeListener(playerListener)
        exoPlayer?.release()
        exoPlayer = null
    }
    
    fun updatePosition() {
        exoPlayer?.let { player ->
            _currentPosition.value = player.currentPosition
            _duration.value = player.duration.coerceAtLeast(0L)
        }
    }
    
    fun selectAudioTrack(groupIndex: Int, trackIndex: Int) {
        exoPlayer?.let { player ->
            val trackSelector = player.trackSelector as? DefaultTrackSelector ?: return
            val tracks = player.currentTracks
            
            tracks.groups.getOrNull(groupIndex)?.let { group ->
                val override = TrackSelectionOverride(group.mediaTrackGroup, trackIndex)
                trackSelector.parameters = trackSelector.buildUponParameters()
                    .setOverrideForType(override)
                    .build()
            }
        }
    }
    
    fun selectSubtitleTrack(groupIndex: Int, trackIndex: Int) {
        exoPlayer?.let { player ->
            val trackSelector = player.trackSelector as? DefaultTrackSelector ?: return
            val tracks = player.currentTracks
            
            tracks.groups.getOrNull(groupIndex)?.let { group ->
                val override = TrackSelectionOverride(group.mediaTrackGroup, trackIndex)
                trackSelector.parameters = trackSelector.buildUponParameters()
                    .setOverrideForType(override)
                    .build()
            }
        }
    }
    
    fun disableSubtitles() {
        exoPlayer?.let { player ->
            val trackSelector = player.trackSelector as? DefaultTrackSelector ?: return
            trackSelector.parameters = trackSelector.buildUponParameters()
                .setIgnoredTextSelectionFlags(C.SELECTION_FLAG_FORCED.inv())
                .build()
        }
    }
    
    sealed class PlayerState {
        data object Idle : PlayerState()
        data object Buffering : PlayerState()
        data object Ready : PlayerState()
        data object Ended : PlayerState()
        data class Error(val message: String) : PlayerState()
    }
}
