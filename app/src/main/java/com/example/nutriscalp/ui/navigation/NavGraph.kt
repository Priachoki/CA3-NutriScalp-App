package com.example.nutriscalp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutriscalp.ui.screens.home.HomeScreen
import com.example.nutriscalp.ui.screens.splash.SplashScreen

@Composable
fun NutriScalpNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController = navController)
        }

        composable("home") {
            HomeScreen()
        }
    }
}