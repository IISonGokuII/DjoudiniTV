package com.nextgen.iptv.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nextgen.iptv.presentation.theme.Dimens

data class DashboardTile(
    val title: String,
    val icon: ImageVector,
    val enabled: Boolean = true,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToLiveTv: () -> Unit,
    onNavigateToVod: () -> Unit,
    onNavigateToSeries: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToProviderSetup: () -> Unit,
    isTv: Boolean = false,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val tiles = listOf(
        DashboardTile(
            title = "Live TV",
            icon = Icons.Default.LiveTv,
            enabled = uiState.hasProviders,
            onClick = onNavigateToLiveTv
        ),
        DashboardTile(
            title = "VOD",
            icon = Icons.Default.Movie,
            enabled = uiState.hasProviders,
            onClick = onNavigateToVod
        ),
        DashboardTile(
            title = "Serien",
            icon = Icons.Default.Tv,
            enabled = uiState.hasProviders,
            onClick = onNavigateToSeries
        ),
        DashboardTile(
            title = "Einstellungen",
            icon = Icons.Default.Settings,
            enabled = true,
            onClick = onNavigateToSettings
        )
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "DjoudiniTV",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToProviderSetup
            ) {
                Icon(Icons.Default.Storage, contentDescription = "Anbieter hinzufügen")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Provider Status Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.paddingMedium),
                colors = CardDefaults.cardColors(
                    containerColor = if (uiState.hasProviders)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(Dimens.paddingMedium)
                ) {
                    Text(
                        text = if (uiState.hasProviders)
                            "${uiState.providers.size} Anbieter verbunden"
                        else
                            "Kein Anbieter verbunden",
                        style = MaterialTheme.typography.titleMedium,
                        color = if (uiState.hasProviders)
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (!uiState.hasProviders) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tippe auf das + Symbol um einen Anbieter hinzuzufügen",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // Dashboard Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Dimens.paddingMedium),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacingMedium),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
            ) {
                items(tiles) { tile ->
                    DashboardTileItem(
                        tile = tile,
                        isTv = isTv
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardTileItem(
    tile: DashboardTile,
    isTv: Boolean
) {
    val focusRequester = FocusRequester()
    
    Card(
        onClick = tile.onClick,
        enabled = tile.enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isTv) Dimens.tileHeightTv else Dimens.tileHeightMobile)
            .focusRequester(focusRequester),
        colors = CardDefaults.cardColors(
            containerColor = if (tile.enabled)
                MaterialTheme.colorScheme.surface
            else
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (tile.enabled) Dimens.tileElevation else 0.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = tile.icon,
                    contentDescription = tile.title,
                    modifier = Modifier.scale(if (isTv) 1.5f else 1.2f),
                    tint = if (tile.enabled)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = tile.title,
                    style = if (isTv) MaterialTheme.typography.titleLarge 
                           else MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = if (tile.enabled)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            }
        }
    }
}
