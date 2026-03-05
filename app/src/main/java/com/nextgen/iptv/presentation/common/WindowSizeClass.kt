package com.nextgen.iptv.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Window Size Class for adaptive layouts
 * COMPACT: Smartphone Portrait
 * MEDIUM: Tablet / Phone Landscape
 * EXPANDED: Android TV / Large Tablets
 */
enum class WindowSizeClass { COMPACT, MEDIUM, EXPANDED }

@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 600 -> WindowSizeClass.COMPACT
        configuration.screenWidthDp < 840 -> WindowSizeClass.MEDIUM
        else -> WindowSizeClass.EXPANDED
    }
}

fun WindowSizeClass.isTv(): Boolean = this == WindowSizeClass.EXPANDED
fun WindowSizeClass.isMobile(): Boolean = this == WindowSizeClass.COMPACT
fun WindowSizeClass.isTablet(): Boolean = this == WindowSizeClass.MEDIUM

@Composable
fun isTvDevice(): Boolean {
    return rememberWindowSizeClass().isTv()
}

object ScreenSize {
    @Composable
    fun width(): Dp = LocalConfiguration.current.screenWidthDp.dp
    
    @Composable
    fun height(): Dp = LocalConfiguration.current.screenHeightDp.dp
}
