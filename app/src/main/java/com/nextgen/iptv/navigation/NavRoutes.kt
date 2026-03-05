package com.nextgen.iptv.navigation

sealed class NavRoutes(val route: String) {
    data object Dashboard : NavRoutes("dashboard")
    data object LiveTv : NavRoutes("live_tv")
    data object Vod : NavRoutes("vod")
    data object Series : NavRoutes("series")
    data object Settings : NavRoutes("settings")
    data object Player : NavRoutes("player/{streamId}") {
        fun createRoute(streamId: String) = "player/$streamId"
    }
    data object ProviderSetup : NavRoutes("provider_setup")
    data object ChannelList : NavRoutes("channel_list/{categoryId}") {
        fun createRoute(categoryId: String) = "channel_list/$categoryId"
    }
}
