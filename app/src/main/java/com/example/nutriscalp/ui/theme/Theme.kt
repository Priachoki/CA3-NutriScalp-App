package com.example.nutriscalp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = AccentTerra, // Accent color for primary elements
    secondary = RichBrown, // Secondary accent
    background = PureCream, // Primary app background (Softest color)
    surface = TextLight, // Card backgrounds (Pure white for contrast)
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