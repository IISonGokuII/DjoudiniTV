package com.nextgen.iptv.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nextgen.iptv.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Einstellungen") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        SettingsContent(
            bufferSizeMs = uiState.settings.playerBufferSizeMs,
            onBufferSizeChange = viewModel::updatePlayerBufferSize,
            epgRefreshHours = uiState.settings.epgRefreshIntervalHours,
            onEpgRefreshChange = viewModel::updateEpgRefreshInterval,
            darkMode = uiState.settings.useDarkMode,
            onDarkModeChange = viewModel::toggleDarkMode,
            onClearCache = viewModel::clearCache,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun SettingsContent(
    bufferSizeMs: Int,
    onBufferSizeChange: (Int) -> Unit,
    epgRefreshHours: Int,
    onEpgRefreshChange: (Int) -> Unit,
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onClearCache: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Appearance
        SettingsSection(title = "Darstellung") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Dunkler Modus")
                Switch(
                    checked = darkMode,
                    onCheckedChange = onDarkModeChange
                )
            }
        }

        Divider()

        // Player
        SettingsSection(title = "Player") {
            Text("Puffergröße: ${bufferSizeMs}ms")
            Slider(
                value = bufferSizeMs.toFloat(),
                onValueChange = { onBufferSizeChange(it.toInt()) },
                valueRange = 500f..5000f,
                steps = 9
            )
        }

        Divider()

        // EPG
        SettingsSection(title = "EPG") {
            Text("Aktualisierungsintervall: ${epgRefreshHours}h")
            Slider(
                value = epgRefreshHours.toFloat(),
                onValueChange = { onEpgRefreshChange(it.toInt()) },
                valueRange = 6f..72f,
                steps = 10
            )
        }

        Divider()

        // Cache
        SettingsSection(title = "Cache") {
            Button(
                onClick = onClearCache,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cache leeren")
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}
