package com.nextgen.iptv.ui.navigation

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
import com.nextgen.iptv.ui.screen.series.SeriesDetailScreen
import com.nextgen.iptv.ui.screen.series.SeriesListScreen
import com.nextgen.iptv.ui.screen.settings.SettingsScreen
import com.nextgen.iptv.ui.screen.setup.ProviderSetupScreen
import com.nextgen.iptv.ui.screen.vod.VodScreen

object NavRoutes {
    const val DASHBOARD = "dashboard"
    const val LIVE_TV = "live_tv"
    const val VOD = "vod"
    const val SERIES = "series"
    const val SERIES_DETAIL = "series_detail/{seriesId}"
    const val SETTINGS = "settings"
    const val PLAYER = "player/{streamUrl}"
    const val PROVIDER_SETUP = "provider_setup"
    
    fun playerRoute(streamUrl: String): String {
        return "player/${java.net.URLEncoder.encode(streamUrl, "UTF-8")}"
    }
    
    fun seriesDetailRoute(seriesId: String): String {
        return "series_detail/$seriesId"
    }
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
                onNavigateToVod = { navController.navigate(NavRoutes.VOD) },
                onNavigateToSeries = { navController.navigate(NavRoutes.SERIES) },
                onNavigateToProviderSetup = { navController.navigate(NavRoutes.PROVIDER_SETUP) },
                onNavigateToSettings = { navController.navigate(NavRoutes.SETTINGS) },
                onNavigateToPlayer = { streamUrl ->
                    navController.navigate(NavRoutes.playerRoute(streamUrl))
                }
            )
        }
        
        composable(NavRoutes.LIVE_TV) {
            LiveTvScreen(
                onNavigateToPlayer = { streamUrl ->
                    navController.navigate(NavRoutes.playerRoute(streamUrl))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(NavRoutes.VOD) {
            VodScreen(
                onNavigateToPlayer = { streamUrl ->
                    navController.navigate(NavRoutes.playerRoute(streamUrl))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(NavRoutes.SETTINGS) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = NavRoutes.PLAYER,
            arguments = listOf(
                navArgument("streamUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("streamUrl") ?: ""
            val streamUrl = java.net.URLDecoder.decode(encodedUrl, "UTF-8")
            PlayerScreen(
                streamUrl = streamUrl,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(NavRoutes.SERIES) {
            SeriesListScreen(
                onNavigateToSeriesDetail = { seriesId ->
                    navController.navigate(NavRoutes.seriesDetailRoute(seriesId))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = NavRoutes.SERIES_DETAIL,
            arguments = listOf(
                navArgument("seriesId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val seriesId = backStackEntry.arguments?.getString("seriesId") ?: ""
            SeriesDetailScreen(
                seriesId = seriesId,
                onNavigateBack = { navController.popBackStack() },
                onPlayEpisode = { streamUrl ->
                    if (streamUrl.isNotEmpty()) {
                        navController.navigate(NavRoutes.playerRoute(streamUrl))
                    }
                }
            )
        }
        
        composable(NavRoutes.PROVIDER_SETUP) {
            ProviderSetupScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
