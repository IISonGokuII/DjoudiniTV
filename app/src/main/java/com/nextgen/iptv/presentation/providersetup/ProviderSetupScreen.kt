package com.nextgen.iptv.presentation.providersetup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nextgen.iptv.presentation.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderSetupScreen(
    onNavigateBack: () -> Unit,
    onSetupComplete: () -> Unit,
    isTv: Boolean = false,
    viewModel: ProviderSetupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Show error in snackbar
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
    }
    
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Anbieter Einrichten") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Dimens.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
        ) {
            // Provider Type Selection
            Text(
                text = "Anbieter Typ",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.selectedType == SetupProviderType.XTREAM,
                    onClick = { viewModel.onProviderTypeSelected(SetupProviderType.XTREAM) },
                    label = { Text("Xtream") },
                    leadingIcon = {
                        Icon(Icons.Default.Cloud, contentDescription = null)
                    }
                )
                FilterChip(
                    selected = uiState.selectedType == SetupProviderType.M3U_URL,
                    onClick = { viewModel.onProviderTypeSelected(SetupProviderType.M3U_URL) },
                    label = { Text("M3U URL") },
                    leadingIcon = {
                        Icon(Icons.Default.Link, contentDescription = null)
                    }
                )
                FilterChip(
                    selected = uiState.selectedType == SetupProviderType.M3U_LOCAL,
                    onClick = { viewModel.onProviderTypeSelected(SetupProviderType.M3U_LOCAL) },
                    label = { Text("Lokal") },
                    leadingIcon = {
                        Icon(Icons.Default.Storage, contentDescription = null)
                    },
                    enabled = false // Not implemented yet
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Provider Name
            OutlinedTextField(
                value = uiState.providerName,
                onValueChange = viewModel::onProviderNameChange,
                label = { Text("Anbieter Name") },
                placeholder = { Text("z.B. Mein IPTV") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            
            // Server URL / M3U URL
            OutlinedTextField(
                value = uiState.serverUrl,
                onValueChange = viewModel::onServerUrlChange,
                label = { 
                    Text(if (uiState.selectedType == SetupProviderType.XTREAM) 
                        "Server URL" else "M3U URL") 
                },
                placeholder = { 
                    Text(if (uiState.selectedType == SetupProviderType.XTREAM)
                        "http://example.com:8080" else "http://example.com/playlist.m3u")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Next
                )
            )
            
            // Xtream Credentials
            if (uiState.selectedType == SetupProviderType.XTREAM) {
                OutlinedTextField(
                    value = uiState.username,
                    onValueChange = viewModel::onUsernameChange,
                    label = { Text("Benutzername") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = { Text("Passwort") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )
            }
            
            // Sync Status
            if (uiState.syncStatus != null) {
                Text(
                    text = uiState.syncStatus!!,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Abbrechen")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { viewModel.addProvider(onSetupComplete) },
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Hinzufügen & Synchronisieren")
                    }
                }
            }
        }
    }
}
