package com.nextgen.iptv.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nextgen.iptv.ui.screen.dashboard.DashboardScreen
import com.nextgen.iptv.ui.screen.livetv.LiveTvScreen
import com.nextgen.iptv.ui.screen.player.PlayerScreen
import com.nextgen.iptv.ui.screen.settings.SettingsScreen
import com.nextgen.iptv.ui.screen.setup.ProviderSetupScreen

object NavRoutes {
    const val DASHBOARD = "dashboard"
    const val LIVE_TV = "live_tv"
    const val VOD = "vod"
    const val SERIES = "series"
    const val SETTINGS = "settings"
    const val PLAYER = "player/{streamId}"
    const val PROVIDER_SETUP = "provider_setup"
    
    fun playerRoute(streamId: String) = "player/$streamId"
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isTv: Boolean = false
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.DASHBOARD,
        modifier = modifier
    ) {
        composable(NavRoutes.DASHBOARD) {
            DashboardScreen(
                onNavigateToLiveTv = { navController.navigate(NavRoutes.LIVE_TV) },
                onNavigateToProviderSetup = { navController.navigate(NavRoutes.PROVIDER_SETUP) },
                onNavigateToSettings = { navController.navigate(NavRoutes.SETTINGS) }
            )
        }
        
        composable(NavRoutes.LIVE_TV) {
            LiveTvScreen(
                onNavigateToPlayer = { streamId ->
                    navController.navigate(NavRoutes.playerRoute(streamId))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(NavRoutes.VOD) {
            // VOD screen - placeholder for now
        }
        
        composable(NavRoutes.SERIES) {
            // Series screen - placeholder for now
        }
        
        composable(NavRoutes.SETTINGS) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = NavRoutes.PLAYER,
            arguments = listOf(
                navArgument("streamId") { type = NavType.StringType }
            )
        ) {
            PlayerScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(NavRoutes.PROVIDER_SETUP) {
            ProviderSetupScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
