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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nextgen.iptv.data.local.entity.ProviderEntity
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
            providers = uiState.providers,
            isSyncing = uiState.isSyncing,
            syncMessage = uiState.syncMessage,
            syncProgress = uiState.syncProgress,
            onSyncProvider = viewModel::syncProviderFull,
            modifier = Modifier.padding(paddingValues)
        )
        
        // Error dialog
        uiState.error?.let { error ->
            AlertDialog(
                onDismissRequest = viewModel::clearError,
                title = { Text("Fehler") },
                text = { Text(error) },
                confirmButton = {
                    TextButton(onClick = viewModel::clearError) {
                        Text("OK")
                    }
                }
            )
        }
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
    providers: List<ProviderEntity>,
    isSyncing: Boolean,
    syncMessage: String,
    syncProgress: Int,
    onSyncProvider: (String) -> Unit,
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

        HorizontalDivider()

        // Player
        SettingsSection(title = "Player") {
            Text("Puffergrosse: ${bufferSizeMs}ms")
            Slider(
                value = bufferSizeMs.toFloat(),
                onValueChange = { onBufferSizeChange(it.toInt()) },
                valueRange = 500f..5000f,
                steps = 9
            )
        }

        HorizontalDivider()

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

        HorizontalDivider()

        // Providers & Sync
        SettingsSection(title = "Provider & Synchronisation") {
            if (providers.isEmpty()) {
                Text(
                    text = "Keine Provider vorhanden",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    providers.forEach { provider ->
                        ProviderSyncCard(
                            provider = provider,
                            isSyncing = isSyncing,
                            syncMessage = syncMessage,
                            syncProgress = syncProgress,
                            onSync = { onSyncProvider(provider.id) }
                        )
                    }
                }
            }
            
            if (isSyncing) {
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    progress = { syncProgress / 100f },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = syncMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        HorizontalDivider()

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
private fun ProviderSyncCard(
    provider: ProviderEntity,
    isSyncing: Boolean,
    syncMessage: String,
    syncProgress: Int,
    onSync: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Tv,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = provider.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = provider.type.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                IconButton(
                    onClick = onSync,
                    enabled = !isSyncing
                ) {
                    if (isSyncing) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(8.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Synchronisieren"
                        )
                    }
                }
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
