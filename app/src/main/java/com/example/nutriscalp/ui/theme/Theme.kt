package com.example.nutriscalp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = AccentPrimary, // 💡 Used AccentPrimary (Deep Teal)
    secondary = AccentSecondary, // 💡 Used AccentSecondary (Light Aqua)
    background = BackgroundLight, // 💡 Used BackgroundLight (Clean Grey)
    surface = SurfaceCard, // 💡 Used SurfaceCard (Pure White)
    onPrimary = TextLight, // Text on primary color
    onSecondary = TextDark,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun NutriScalpTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}