package com.example.nutriscalp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.example.nutriscalp.AppViewModel

private val DarkColorScheme = darkColorScheme(
    primary = AccentDark, // Lighter accent for dark background
    secondary = AccentSecondary,
    background = BackgroundDark, // Black/Dark Grey
    surface = SurfaceDark, // Darker surface for cards
    onPrimary = TextDark, // Text on primary color
    onSecondary = TextLight,
    onBackground = TextLight, // White text on dark background
    onSurface = TextLight
)

private val LightColorScheme = lightColorScheme(
    primary = AccentPrimary, // Deep Teal
    secondary = AccentSecondary, // Light Aqua
    background = BackgroundLight, // Clean Grey
    surface = SurfaceCard, // Pure White
    onPrimary = TextLight, // Text on primary color
    onSecondary = TextDark,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun NutriScalpTheme(
    appViewModel: AppViewModel,
    content: @Composable () -> Unit
) {

    val isDarkMode by appViewModel.isDarkMode.collectAsState()

    val colorScheme = if (isDarkMode) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}