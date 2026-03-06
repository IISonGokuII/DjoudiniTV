package com.nextgen.iptv.presentation.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nextgen.iptv.data.local.entity.CategoryEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Einrichtung") },
                actions = {
                    // Progress indicator
                    LinearProgressIndicator(
                        progress = { calculateProgress(uiState) },
                        modifier = Modifier
                            .width(100.dp)
                            .padding(end = 16.dp)
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            AnimatedContent(
                targetState = uiState.currentStep,
                transitionSpec = {
                    slideInHorizontally(
                        animationSpec = tween(300),
                        initialOffsetX = { it }
                    ) + fadeIn() togetherWith
                    slideOutHorizontally(
                        animationSpec = tween(300),
                        targetOffsetX = { -it }
                    ) + fadeOut()
                }
            ) { step ->
                when (step) {
                    is OnboardingStep.Welcome -> WelcomeStep(
                        onContinue = { viewModel.goToStep(OnboardingStep.ProviderType) }
                    )
                    is OnboardingStep.ProviderType -> ProviderTypeStep(
                        selectedType = uiState.providerType,
                        onTypeSelected = viewModel::selectProviderType,
                        onContinue = {
                            when (uiState.providerType) {
                                ProviderTypeSelection.XTREAM -> viewModel.goToStep(OnboardingStep.XtreamLogin)
                                ProviderTypeSelection.M3U_URL -> viewModel.goToStep(OnboardingStep.M3UUrl)
                                else -> {}
                            }
                        }
                    )
                    is OnboardingStep.XtreamLogin -> XtreamLoginStep(
                        serverUrl = uiState.serverUrl,
                        username = uiState.username,
                        password = uiState.password,
                        isLoading = uiState.isLoading,
                        error = uiState.error,
                        onServerUrlChange = viewModel::updateServerUrl,
                        onUsernameChange = viewModel::updateUsername,
                        onPasswordChange = viewModel::updatePassword,
                        onConnect = { viewModel.connectProvider() }
                    )
                    is OnboardingStep.M3UUrl -> M3UUrlStep(
                        url = uiState.m3uUrl,
                        isLoading = uiState.isLoading,
                        error = uiState.error,
                        onUrlChange = viewModel::updateM3uUrl,
                        onConnect = { viewModel.connectProvider() }
                    )
                    is OnboardingStep.Validating -> ValidatingStep(
                        message = uiState.validationMessage
                    )
                    is OnboardingStep.Syncing -> SyncingStep(
                        message = uiState.syncMessage,
                        progress = uiState.syncProgress
                    )
                    is OnboardingStep.SelectLiveTvCategories -> {
                        SequentialCategorySelectionStep(
                            title = "Live TV Kategorien",
                            subtitle = "Welche Live TV Kategorien möchtest du sehen?",
                            icon = Icons.Default.LiveTv,
                            categories = step.categories,
                            selectedIds = step.selectedIds,
                            onToggle = { viewModel.updateLiveTvSelection(step.selectedIds.toggle(it)) },
                            onSelectAll = { viewModel.updateLiveTvSelection(step.categories.map { it.id }.toSet()) },
                            onDeselectAll = { viewModel.updateLiveTvSelection(emptySet()) },
                            onContinue = { viewModel.confirmLiveTvSelectionAndContinue() },
                            continueButtonText = "Weiter zu Serien"
                        )
                    }
                    is OnboardingStep.SelectSeriesCategories -> {
                        SequentialCategorySelectionStep(
                            title = "Serien-Kategorien",
                            subtitle = "Welche Serien-Kategorien möchtest du sehen?",
                            icon = Icons.Default.Tv,
                            categories = step.categories,
                            selectedIds = step.selectedIds,
                            onToggle = { viewModel.updateSeriesSelection(step.selectedIds.toggle(it)) },
                            onSelectAll = { viewModel.updateSeriesSelection(step.categories.map { it.id }.toSet()) },
                            onDeselectAll = { viewModel.updateSeriesSelection(emptySet()) },
                            onContinue = { viewModel.confirmSeriesSelectionAndContinue() },
                            continueButtonText = "Weiter zu Filmen"
                        )
                    }
                    is OnboardingStep.SelectVodCategories -> {
                        SequentialCategorySelectionStep(
                            title = "Film-Kategorien",
                            subtitle = "Welche Film-Kategorien möchtest du sehen?",
                            icon = Icons.Default.Movie,
                            categories = step.categories,
                            selectedIds = step.selectedIds,
                            onToggle = { viewModel.updateVodSelection(step.selectedIds.toggle(it)) },
                            onSelectAll = { viewModel.updateVodSelection(step.categories.map { it.id }.toSet()) },
                            onDeselectAll = { viewModel.updateVodSelection(emptySet()) },
                            onContinue = { viewModel.confirmVodSelectionAndFinish(onComplete) },
                            continueButtonText = "App starten"
                        )
                    }
                    is OnboardingStep.Complete -> CompleteStep(
                        onFinish = onComplete
                    )
                }
            }
        }
    }
}

private fun calculateProgress(state: OnboardingUiState): Float {
    return when (state.currentStep) {
        is OnboardingStep.Welcome -> 0.1f
        is OnboardingStep.ProviderType -> 0.2f
        is OnboardingStep.XtreamLogin -> 0.3f
        is OnboardingStep.M3UUrl -> 0.3f
        is OnboardingStep.Validating -> 0.4f
        is OnboardingStep.Syncing -> 0.4f + (state.syncProgress / 100f * 0.2f)
        is OnboardingStep.SelectLiveTvCategories -> 0.65f
        is OnboardingStep.SelectSeriesCategories -> 0.8f
        is OnboardingStep.SelectVodCategories -> 0.9f
        is OnboardingStep.Complete -> 1f
    }
}

@Composable
private fun WelcomeStep(
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Tv,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Willkommen bei DjoudiniTV",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Lassen Sie uns Ihre App einrichten. In wenigen Schritten können Sie Ihre IPTV-Inhalte genießen.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Los geht's")
        }
    }
}

@Composable
private fun ProviderTypeStep(
    selectedType: ProviderTypeSelection,
    onTypeSelected: (ProviderTypeSelection) -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Wählen Sie Ihren Provider-Typ",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Wie möchten Sie Ihre IPTV-Inhalte hinzufügen?",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Xtream Codes Option
        ElevatedCard(
            onClick = { onTypeSelected(ProviderTypeSelection.XTREAM) },
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(
                containerColor = if (selectedType == ProviderTypeSelection.XTREAM) 
                    MaterialTheme.colorScheme.primaryContainer 
                else 
                    MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Tv,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Xtream Codes API",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Mit Benutzername, Passwort und Server-URL verbinden",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                if (selectedType == ProviderTypeSelection.XTREAM) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // M3U URL Option
        ElevatedCard(
            onClick = { onTypeSelected(ProviderTypeSelection.M3U_URL) },
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(
                containerColor = if (selectedType == ProviderTypeSelection.M3U_URL) 
                    MaterialTheme.colorScheme.primaryContainer 
                else 
                    MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Movie,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "M3U URL",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Playlist über URL-Link hinzufügen",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                if (selectedType == ProviderTypeSelection.M3U_URL) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onContinue,
            enabled = selectedType != ProviderTypeSelection.NONE,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Weiter")
        }
    }
}

@Composable
private fun XtreamLoginStep(
    serverUrl: String,
    username: String,
    password: String,
    isLoading: Boolean,
    error: String?,
    onServerUrlChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConnect: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Xtream Codes Login",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Geben Sie Ihre Zugangsdaten ein",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = serverUrl,
            onValueChange = onServerUrlChange,
            label = { Text("Server URL") },
            placeholder = { Text("http://example.com:8080") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Benutzername") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Passwort") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )
        
        error?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onConnect,
            enabled = !isLoading && serverUrl.isNotBlank() && username.isNotBlank() && password.isNotBlank(),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Verbinden")
            }
        }
    }
}

@Composable
private fun M3UUrlStep(
    url: String,
    isLoading: Boolean,
    error: String?,
    onUrlChange: (String) -> Unit,
    onConnect: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "M3U URL",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Geben Sie die URL Ihrer M3U-Playlist ein",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = url,
            onValueChange = onUrlChange,
            label = { Text("M3U URL") },
            placeholder = { Text("http://example.com/playlist.m3u") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )
        
        error?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onConnect,
            enabled = !isLoading && url.isNotBlank(),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Verbinden")
            }
        }
    }
}

@Composable
private fun LoadingStep() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Synchronisiere...",
            style = MaterialTheme.typography.headlineSmall
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Ihre Inhalte werden geladen",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ValidatingStep(
    message: String?
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Verbindung wird geprüft...",
            style = MaterialTheme.typography.headlineSmall
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Max. 30 Sekunden",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        message?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun SyncingStep(
    message: String,
    progress: Int
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Progress circle with percentage
        Box(
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(120.dp),
                strokeWidth = 8.dp
            )
            Text(
                text = "$progress%",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = message,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Live TV, Film- und Serien-Kategorien werden geladen",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LinearProgressIndicator(
            progress = { progress / 100f },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategorySelectionStep(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    categories: List<CategoryEntity>,
    selectedIds: Set<String>,
    onToggle: (String) -> Unit,
    onSelectAll: () -> Unit,
    onDeselectAll: () -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Selection actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onSelectAll,
                modifier = Modifier.weight(1f)
            ) {
                Text("Alle auswählen")
            }
            
            OutlinedButton(
                onClick = onDeselectAll,
                modifier = Modifier.weight(1f)
            ) {
                Text("Alle abwählen")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "${selectedIds.size} von ${categories.size} ausgewählt",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Categories list
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = selectedIds.contains(category.id)
                
                Card(
                    onClick = { onToggle(category.id) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                        
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Continue button
        Button(
            onClick = onContinue,
            enabled = selectedIds.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Weiter")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun CategorySection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    categories: List<CategoryEntity>,
    selectedIds: Set<String>,
    onToggle: (String) -> Unit,
    onSelectAll: () -> Unit,
    onDeselectAll: () -> Unit
) {
    Column {
        // Section header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            
            // Select/Deselect all buttons
            TextButton(onClick = onSelectAll) {
                Text("Alle")
            }
            
            TextButton(onClick = onDeselectAll) {
                Text("Keine")
            }
        }
        
        Text(
            text = "${selectedIds.size} von ${categories.size} ausgewahlt",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 32.dp, bottom = 8.dp)
        )
        
        // Categories chips
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                val isSelected = selectedIds.contains(category.id)
                
                FilterChip(
                    selected = isSelected,
                    onClick = { onToggle(category.id) },
                    label = { Text(category.name) },
                    leadingIcon = if (isSelected) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else null
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun SequentialCategorySelectionStep(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    categories: List<CategoryEntity>,
    selectedIds: Set<String>,
    onToggle: (String) -> Unit,
    onSelectAll: () -> Unit,
    onDeselectAll: () -> Unit,
    onContinue: () -> Unit,
    continueButtonText: String
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Selection actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onSelectAll,
                modifier = Modifier.weight(1f)
            ) {
                Text("Alle auswählen")
            }
            
            OutlinedButton(
                onClick = onDeselectAll,
                modifier = Modifier.weight(1f)
            ) {
                Text("Alle abwählen")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "${selectedIds.size} von ${categories.size} ausgewählt",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Categories list
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = selectedIds.contains(category.id)
                
                Card(
                    onClick = { onToggle(category.id) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                        
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Continue button
        Button(
            onClick = onContinue,
            enabled = selectedIds.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(continueButtonText)
        }
    }
}

@Composable
private fun CompleteStep(
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Einrichtung abgeschlossen!",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Ihre App ist jetzt bereit. Viel Spaß mit DjoudiniTV!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = onFinish,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("App starten")
        }
    }
}

// Helper extension to toggle a value in a Set
fun Set<String>.toggle(value: String): Set<String> {
    return if (this.contains(value)) this - value else this + value
}
