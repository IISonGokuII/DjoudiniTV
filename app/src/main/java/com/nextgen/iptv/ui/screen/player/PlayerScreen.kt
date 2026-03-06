package com.nextgen.iptv.ui.screen.player

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.nextgen.iptv.ui.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    streamUrl: String,
    onNavigateBack: () -> Unit,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val uiState by viewModel.uiState.collectAsState()
    
    var isFullscreen by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showResumeDialog by remember { mutableStateOf(false) }

    // Handle fullscreen mode
    LaunchedEffect(isFullscreen) {
        activity?.let { act ->
            val window = act.window
            val controller = window.insetsController
            
            if (isFullscreen) {
                controller?.let { ctrl ->
                    ctrl.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    ctrl.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
                act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                controller?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
    }

    // Cleanup on dispose
    DisposableEffect(Unit) {
        onDispose {
            activity?.let { act ->
                val controller = act.window.insetsController
                controller?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
    }

    // Show resume dialog if there's saved progress
    LaunchedEffect(uiState.savedProgressMs) {
        if (uiState.savedProgressMs > 0) {
            showResumeDialog = true
        }
    }

    // Resume Dialog
    if (showResumeDialog) {
        AlertDialog(
            onDismissRequest = { showResumeDialog = false },
            title = { Text("Weiterschauen?") },
            text = { Text("Möchten Sie bei ${formatTime(uiState.savedProgressMs)} weitermachen?") },
            confirmButton = {
                TextButton(onClick = { showResumeDialog = false }) {
                    Text("Ja, weiter")
                }
            },
            dismissButton = {
                TextButton(onClick = { 
                    showResumeDialog = false
                    // Reset progress to start
                    viewModel.clearError()
                }) {
                    Text("Nein, von Anfang")
                }
            }
        )
    }

    if (isFullscreen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            PlayerContent(
                streamUrl = streamUrl,
                savedProgressMs = uiState.savedProgressMs,
                isFullscreen = true,
                onFullscreenToggle = { isFullscreen = false },
                onNavigateBack = {
                    isFullscreen = false
                    onNavigateBack()
                },
                onLoadingChange = { isLoading = it },
                onError = { errorMessage = it },
                onSaveProgress = { position, duration ->
                    viewModel.saveProgress(position, duration)
                }
            )
            
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Wiedergabe") },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Zurück")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(Color.Black)
                ) {
                    PlayerContent(
                        streamUrl = streamUrl,
                        savedProgressMs = uiState.savedProgressMs,
                        isFullscreen = false,
                        onFullscreenToggle = { isFullscreen = true },
                        onNavigateBack = onNavigateBack,
                        onLoadingChange = { isLoading = it },
                        onError = { errorMessage = it },
                        onSaveProgress = { position, duration ->
                            viewModel.saveProgress(position, duration)
                        }
                    )
                    
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )
                    }
                }
                
                // Stream info below player
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Stream URL",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = streamUrl,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    errorMessage?.let { error ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Fehler: $error",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun PlayerContent(
    streamUrl: String,
    savedProgressMs: Long,
    isFullscreen: Boolean,
    onFullscreenToggle: () -> Unit,
    onNavigateBack: () -> Unit,
    onLoadingChange: (Boolean) -> Unit,
    onError: (String?) -> Unit,
    onSaveProgress: (Long, Long) -> Unit
) {
    val context = LocalContext.current
    var player by remember { mutableStateOf<ExoPlayer?>(null) }

    DisposableEffect(streamUrl) {
        val exoPlayer = ExoPlayer.Builder(context)
            .setRenderersFactory(DefaultRenderersFactory(context).setExtensionRendererMode(
                DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON
            ))
            .setLoadControl(
                DefaultLoadControl.Builder()
                    .setBufferDurationsMs(
                        30000,
                        60000,
                        1000,
                        5000
                    )
                    .build()
            )
            .build()
            .apply {
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        when (state) {
                            Player.STATE_BUFFERING -> {
                                onLoadingChange(true)
                                onError(null)
                            }
                            Player.STATE_READY -> {
                                onLoadingChange(false)
                                onError(null)
                            }
                            Player.STATE_IDLE -> {
                                onLoadingChange(false)
                            }
                            Player.STATE_ENDED -> {
                                onLoadingChange(false)
                            }
                        }
                    }

                    override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                        onLoadingChange(false)
                        onError(error.message ?: "Unbekannter Fehler")
                    }
                })
            }
        
        player = exoPlayer

        // Prepare and play
        try {
            val mediaItem = MediaItem.fromUri(streamUrl)
            exoPlayer.setMediaItem(mediaItem)
            
            // Seek to saved position if available
            if (savedProgressMs > 0) {
                exoPlayer.seekTo(savedProgressMs)
            }
            
            exoPlayer.prepare()
            exoPlayer.play()
        } catch (e: Exception) {
            onError(e.message ?: "Fehler beim Laden des Streams")
        }

        onDispose {
            // Save progress before releasing
            val currentPosition = exoPlayer.currentPosition
            val duration = exoPlayer.duration
            if (duration > 0) {
                onSaveProgress(currentPosition, duration)
            }
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                this.player = player
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                useController = true
                controllerHideOnTouch = true
                controllerAutoShow = true
                keepScreenOn = true
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { playerView ->
            playerView.player = player
        }
    )
}

private fun formatTime(ms: Long): String {
    val seconds = ms / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    return if (hours > 0) {
        String.format("%d:%02d:%02d", hours, minutes % 60, seconds % 60)
    } else {
        String.format("%d:%02d", minutes, seconds % 60)
    }
}
