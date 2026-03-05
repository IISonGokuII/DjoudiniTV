package com.nextgen.iptv.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    isTv: Boolean = false
) {
    var showClearDataDialog by remember { mutableStateOf(false) }
    var autoSyncEpg by remember { mutableStateOf(true) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Einstellungen") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Data Section
            item {
                Text(
                    text = "Daten",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            item {
                ListItem(
                    headlineContent = { Text("EPG automatisch synchronisieren") },
                    supportingContent = { Text("Tägliche Aktualisierung der Programmdaten") },
                    leadingContent = { Icon(Icons.Default.Refresh, contentDescription = null) },
                    trailingContent = {
                        Switch(
                            checked = autoSyncEpg,
                            onCheckedChange = { autoSyncEpg = it }
                        )
                    }
                )
            }
            
            item {
                ListItem(
                    headlineContent = { Text("Daten löschen") },
                    supportingContent = { Text("Alle Anbieter und gespeicherte Daten entfernen") },
                    leadingContent = { Icon(Icons.Default.ClearAll, contentDescription = null) },
                    modifier = Modifier.clickable { showClearDataDialog = true }
                )
            }
            
            item { Divider(modifier = Modifier.padding(vertical = 8.dp)) }
            
            // Storage Section
            item {
                Text(
                    text = "Speicher",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            item {
                ListItem(
                    headlineContent = { Text("Cache leeren") },
                    supportingContent = { Text("Temporäre Dateien entfernen") },
                    leadingContent = { Icon(Icons.Default.Storage, contentDescription = null) }
                )
            }
            
            item { Divider(modifier = Modifier.padding(vertical = 8.dp)) }
            
            // Info Section
            item {
                Text(
                    text = "Info",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            item {
                ListItem(
                    headlineContent = { Text("Version") },
                    supportingContent = { Text("1.0.0") },
                    leadingContent = { Icon(Icons.Default.Info, contentDescription = null) }
                )
            }
        }
    }
    
    // Clear Data Dialog
    if (showClearDataDialog) {
        AlertDialog(
            onDismissRequest = { showClearDataDialog = false },
            title = { Text("Daten löschen?") },
            text = { Text("Dies wird alle Anbieter, Kanäle und EPG-Daten löschen. Diese Aktion kann nicht rückgängig gemacht werden.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // TODO: Clear all data
                        showClearDataDialog = false
                    }
                ) {
                    Text("Löschen", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDataDialog = false }) {
                    Text("Abbrechen")
                }
            }
        )
    }
}
