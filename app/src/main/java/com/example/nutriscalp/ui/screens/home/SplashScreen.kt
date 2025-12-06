package com.example.nutriscalp.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nutriscalp.R
// 🚨 UPDATED IMPORT
import com.example.nutriscalp.ui.theme.BackgroundLight // 💡 NEW
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {
    // Animation: Use Animatable for a simple fade-in effect
    val alpha = remember {
        Animatable(0f)
    }

    // Animation and Navigation logic
    LaunchedEffect(key1 = true) {
        // Animation: Fade in the logo over 1.5 seconds
        alpha.animateTo(1f, animationSpec = tween(1500))

        // Wait for an additional 1.5 seconds after the animation finishes
        delay(1500L)

        // Navigation: Navigate to the login screen
        onNavigateToHome()
    }

    Box(
        modifier = Modifier
            .background(BackgroundLight) // 💡 Changed from PureCream
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.nutriscalp_logo),
            contentDescription = "NutriScalp Logo",
            modifier = Modifier
                .size(250.dp)
                .alpha(alpha.value) // Apply the fade-in animation value
        )
    }
}