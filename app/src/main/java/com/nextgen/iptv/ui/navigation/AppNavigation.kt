package com.nextgen.iptv.ui.navigation

import androidx.compose.runtime.Composable
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
import com.nextgen.iptv.ui.screen.vod.VodScreen
import com.nextgen.iptv.ui.screen.favorites.FavoritesScreen

sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object ProviderSetup : Screen("provider_setup")
    data object LiveTv : Screen("live_tv")
    data object Vod : Screen("vod")
    data object Series : Screen("series")
    data object Player : Screen("player/{streamId}") {
        fun createRoute(streamId: String) = "player/$streamId"
    }
    data object Settings : Screen("settings")
    data object Favorites : Screen("favorites")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToProviderSetup = {
                    navController.navigate(Screen.ProviderSetup.route)
                },
                onNavigateToLiveTv = {
                    navController.navigate(Screen.LiveTv.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(Screen.ProviderSetup.route) {
            ProviderSetupScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.LiveTv.route) {
            LiveTvScreen(
                onNavigateToPlayer = { streamId ->
                    navController.navigate(Screen.Player.createRoute(streamId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Vod.route) {
            VodScreen(
                onNavigateToPlayer = { streamId ->
                    navController.navigate(Screen.Player.createRoute(streamId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Player.route,
            arguments = listOf(
                navArgument("streamId") { type = NavType.StringType }
            )
        ) {
            PlayerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onNavigateToPlayer = { streamId ->
                    navController.navigate(Screen.Player.createRoute(streamId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
