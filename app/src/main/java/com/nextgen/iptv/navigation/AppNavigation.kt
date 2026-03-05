package com.nextgen.iptv.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nextgen.iptv.presentation.dashboard.DashboardScreen
import com.nextgen.iptv.presentation.livetv.LiveTvScreen
import com.nextgen.iptv.presentation.vod.VodScreen
import com.nextgen.iptv.presentation.series.SeriesScreen
import com.nextgen.iptv.presentation.settings.SettingsScreen
import com.nextgen.iptv.presentation.player.PlayerScreen
import com.nextgen.iptv.presentation.providersetup.ProviderSetupScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isTv: Boolean = false
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Dashboard.route,
        modifier = modifier
    ) {
        composable(NavRoutes.Dashboard.route) {
            DashboardScreen(
                onNavigateToLiveTv = { navController.navigate(NavRoutes.LiveTv.route) },
                onNavigateToVod = { navController.navigate(NavRoutes.Vod.route) },
                onNavigateToSeries = { navController.navigate(NavRoutes.Series.route) },
                onNavigateToSettings = { navController.navigate(NavRoutes.Settings.route) },
                onNavigateToProviderSetup = { navController.navigate(NavRoutes.ProviderSetup.route) },
                isTv = isTv
            )
        }
        
        composable(NavRoutes.LiveTv.route) {
            LiveTvScreen(
                onNavigateBack = { navController.popBackStack() },
                onChannelSelected = { streamId ->
                    navController.navigate(NavRoutes.Player.createRoute(streamId))
                },
                isTv = isTv
            )
        }
        
        composable(NavRoutes.Vod.route) {
            VodScreen(
                onNavigateBack = { navController.popBackStack() },
                onMovieSelected = { streamId ->
                    navController.navigate(NavRoutes.Player.createRoute(streamId))
                },
                isTv = isTv
            )
        }
        
        composable(NavRoutes.Series.route) {
            SeriesScreen(
                onNavigateBack = { navController.popBackStack() },
                onSeriesSelected = { seriesId ->
                    // Navigate to series detail
                },
                isTv = isTv
            )
        }
        
        composable(NavRoutes.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                isTv = isTv
            )
        }
        
        composable(NavRoutes.Player.route) { backStackEntry ->
            val streamId = backStackEntry.arguments?.getString("streamId") ?: ""
            PlayerScreen(
                streamId = streamId,
                onNavigateBack = { navController.popBackStack() },
                isTv = isTv
            )
        }
        
        composable(NavRoutes.ProviderSetup.route) {
            ProviderSetupScreen(
                onNavigateBack = { navController.popBackStack() },
                onSetupComplete = { navController.popBackStack() },
                isTv = isTv
            )
        }
    }
}
