package com.nextgen.iptv.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Dark Theme Colors (Primary - for IPTV apps)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFE94560),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF533F3F),
    onPrimaryContainer = Color(0xFFFFDAD7),
    secondary = Color(0xFF16213E),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF3D5A80),
    onSecondaryContainer = Color.White,
    tertiary = Color(0xFF0F3460),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF1A1A2E),
    onTertiaryContainer = Color.White,
    background = Color(0xFF0F0F1A),
    onBackground = Color(0xFFEAEAEA),
    surface = Color(0xFF1A1A2E),
    onSurface = Color(0xFFEAEAEA),
    surfaceVariant = Color(0xFF252540),
    onSurfaceVariant = Color(0xFFB0B0C0),
    error = Color(0xFFFF4444),
    onError = Color.White,
    outline = Color(0xFF4A4A6A)
)

// Light Theme Colors (rarely used for IPTV, but available)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFE94560),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDAD7),
    onPrimaryContainer = Color(0xFF410005),
    secondary = Color(0xFF16213E),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD6E3FF),
    onSecondaryContainer = Color(0xFF001B3D),
    tertiary = Color(0xFF0F3460),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFD7E2FF),
    onTertiaryContainer = Color(0xFF001945),
    background = Color(0xFFF8F9FA),
    onBackground = Color(0xFF1A1C1E),
    surface = Color.White,
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE0E2EC),
    onSurfaceVariant = Color(0xFF43474E),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    outline = Color(0xFF74777F)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = true, // IPTV apps default to dark theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
