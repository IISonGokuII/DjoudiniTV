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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nextgen.iptv.presentation.theme.Dimens

data class DashboardTile(
    val title: String,
    val icon: ImageVector,
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
    isTv: Boolean = false
) {
    val tiles = listOf(
        DashboardTile("Live TV", Icons.Default.LiveTv, onNavigateToLiveTv),
        DashboardTile("VOD", Icons.Default.Movie, onNavigateToVod),
        DashboardTile("Serien", Icons.Default.Tv, onNavigateToSeries),
        DashboardTile("Anbieter", Icons.Default.Storage, onNavigateToProviderSetup),
        DashboardTile("Einstellungen", Icons.Default.Settings, onNavigateToSettings)
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
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = if (isTv) GridCells.Fixed(2) else GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
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

@Composable
private fun DashboardTileItem(
    tile: DashboardTile,
    isTv: Boolean
) {
    val focusRequester = FocusRequester()
    
    Card(
        onClick = tile.onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isTv) Dimens.tileHeightTv else Dimens.tileHeightMobile)
            .focusRequester(focusRequester)
            .then(
                if (isTv) {
                    Modifier.onFocusChanged { /* Handle focus animation if needed */ }
                } else Modifier
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.tileElevation
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
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = tile.title,
                    style = if (isTv) MaterialTheme.typography.titleLarge 
                           else MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
