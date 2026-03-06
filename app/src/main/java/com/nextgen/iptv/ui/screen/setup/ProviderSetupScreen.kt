package com.nextgen.iptv.ui.screen.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nextgen.iptv.ui.viewmodel.ProviderSetupUiState
import com.nextgen.iptv.ui.viewmodel.ProviderSetupViewModel
import com.nextgen.iptv.ui.viewmodel.SetupProviderType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderSetupScreen(
    onNavigateBack: () -> Unit,
    viewModel: ProviderSetupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(uiState.success) {
        if (uiState.success) {
            onNavigateBack()
            viewModel.reset()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Provider hinzufugen") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zuruck")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        ProviderSetupContent(
            uiState = uiState,
            onNameChange = viewModel::onNameChange,
            onTypeChange = viewModel::onTypeChange,
            onServerUrlChange = viewModel::onServerUrlChange,
            onUsernameChange = viewModel::onUsernameChange,
            onPasswordChange = viewModel::onPasswordChange,
            onM3uUrlChange = viewModel::onM3uUrlChange,
            onAddProvider = { viewModel.validateAndAddProvider(onNavigateBack) },
            onDismissError = viewModel::clearError,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun ProviderSetupContent(
    uiState: ProviderSetupUiState,
    onNameChange: (String) -> Unit,
    onTypeChange: (SetupProviderType) -> Unit,
    onServerUrlChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onM3uUrlChange: (String) -> Unit,
    onAddProvider: () -> Unit,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Name
        OutlinedTextField(
            value = uiState.name,
            onValueChange = onNameChange,
            label = { Text("Name") },
            placeholder = { Text("Mein IPTV Provider") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            enabled = !uiState.isLoading && !uiState.isValidating
        )

        // Provider Type Dropdown
        ProviderTypeDropdown(
            selectedType = uiState.selectedType,
            onTypeChange = onTypeChange,
            enabled = !uiState.isLoading && !uiState.isValidating
        )

        // Type-specific fields
        when (uiState.selectedType) {
            SetupProviderType.XTREAM -> {
                XtreamFields(
                    serverUrl = uiState.serverUrl,
                    username = uiState.username,
                    password = uiState.password,
                    onServerUrlChange = onServerUrlChange,
                    onUsernameChange = onUsernameChange,
                    onPasswordChange = onPasswordChange,
                    enabled = !uiState.isLoading && !uiState.isValidating
                )
            }
            SetupProviderType.M3U_URL -> {
                M3uUrlFields(
                    url = uiState.m3uUrl,
                    onUrlChange = onM3uUrlChange,
                    enabled = !uiState.isLoading && !uiState.isValidating
                )
            }
            SetupProviderType.M3U_LOCAL -> {
                Text(
                    text = "Lokale M3U-Dateien werden noch nicht unterstutzt.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Validation message
        uiState.validationMessage?.let { message ->
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Progress indicator when validating or syncing
        if (uiState.isValidating || uiState.isLoading) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.isValidating) {
                    CircularProgressIndicator(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Verbindung wird uberpruft...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LinearProgressIndicator(
                        progress = { uiState.syncProgress / 100f },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = uiState.syncMessage,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${uiState.syncProgress}%",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Add Button
        Button(
            onClick = onAddProvider,
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading && !uiState.isValidating && uiState.selectedType != SetupProviderType.M3U_LOCAL
        ) {
            if (uiState.isLoading || uiState.isValidating) {
                CircularProgressIndicator(
                    modifier = Modifier.height(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Provider hinzufugen")
            }
        }
        
        // Info text
        if (!uiState.isLoading && !uiState.isValidating) {
            Text(
                text = "Hinweis: Nur Live TV wird beim ersten Login synchronisiert. VOD und Serien konnen spater uber die Einstellungen synchronisiert werden.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

    // Error Dialog
    uiState.error?.let { error ->
        AlertDialog(
            onDismissRequest = onDismissError,
            title = { Text("Fehler") },
            text = { Text(error) },
            confirmButton = {
                TextButton(onClick = onDismissError) {
                    Text("OK")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProviderTypeDropdown(
    selectedType: SetupProviderType,
    onTypeChange: (SetupProviderType) -> Unit,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (enabled) expanded = it }
    ) {
        OutlinedTextField(
            value = when (selectedType) {
                SetupProviderType.XTREAM -> "Xtream Codes API"
                SetupProviderType.M3U_URL -> "M3U Playlist URL"
                SetupProviderType.M3U_LOCAL -> "Lokale M3U-Datei"
            },
            onValueChange = {},
            readOnly = true,
            label = { Text("Provider-Typ") },
            trailingIcon = { 
                if (enabled) {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            enabled = enabled
        )
        
        if (enabled) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Xtream Codes API") },
                    onClick = {
                        onTypeChange(SetupProviderType.XTREAM)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("M3U Playlist URL") },
                    onClick = {
                        onTypeChange(SetupProviderType.M3U_URL)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun XtreamFields(
    serverUrl: String,
    username: String,
    password: String,
    onServerUrlChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    enabled: Boolean = true
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = serverUrl,
            onValueChange = onServerUrlChange,
            label = { Text("Server URL") },
            placeholder = { Text("http://example.com:8080") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Next
            ),
            enabled = enabled
        )

        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Benutzername") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            enabled = enabled
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Passwort") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            enabled = enabled
        )
    }
}

@Composable
private fun M3uUrlFields(
    url: String,
    onUrlChange: (String) -> Unit,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = url,
        onValueChange = onUrlChange,
        label = { Text("M3U URL") },
        placeholder = { Text("http://example.com/playlist.m3u") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Uri,
            imeAction = ImeAction.Done
        ),
        enabled = enabled
    )
}
