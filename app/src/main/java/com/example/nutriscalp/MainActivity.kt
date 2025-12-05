package com.example.nutriscalp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutriscalp.data.DataStoreManager
import com.example.nutriscalp.data.FoodService
import com.example.nutriscalp.ui.screens.DietLogScreen // <-- NEW IMPORT
import com.example.nutriscalp.ui.screens.FoodsScreen
import com.example.nutriscalp.ui.screens.SettingsScreen
import com.example.nutriscalp.ui.screens.TipsScreen
import com.example.nutriscalp.ui.screens.LoginScreen
import com.example.nutriscalp.ui.screens.SplashScreen
import com.example.nutriscalp.ui.screens.home.HomeScreen
import com.example.nutriscalp.ui.theme.NutriScalpTheme
import kotlinx.coroutines.flow.flowOf

// Initialization of DataStoreManager outside of a composable to be reused
private lateinit var dataStoreManager: DataStoreManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        dataStoreManager = DataStoreManager(applicationContext)

        setContent {
            // Architecture Components: ViewModel instantiation
            val appViewModel: AppViewModel = viewModel(
                factory = AppViewModel.factory(FoodService.instance, dataStoreManager)
            )

            NutriScalpApp(appViewModel)
        }
    }
}

@Composable
fun NutriScalpApp(appViewModel: AppViewModel) {
    val navController = rememberNavController()
    // Navigation between Screens
    NutriScalpTheme {
        NavHost(
            navController = navController,
            startDestination = AppDestinations.SPLASH_ROUTE
        ) {
            // SPLASH SCREEN (Animation) -> LOGIN
            composable(AppDestinations.SPLASH_ROUTE) {
                SplashScreen(onNavigateToHome = {
                    navController.popBackStack()
                    navController.navigate(AppDestinations.LOGIN_ROUTE)
                })
            }

            // LOGIN SCREEN -> HOME
            composable(AppDestinations.LOGIN_ROUTE) {
                LoginScreen(onLoginSuccess = {
                    // Navigate to HOME and clear all previous screens (Splash/Login)
                    navController.popBackStack(AppDestinations.LOGIN_ROUTE, inclusive = true)
                    navController.navigate(AppDestinations.HOME_ROUTE)
                })
            }

            // HOME SCREEN (Main Content)
            composable(AppDestinations.HOME_ROUTE) {
                HomeScreen(
                    appViewModel = appViewModel,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }

            // FOODS SCREEN
            composable(AppDestinations.FOODS_ROUTE) {
                FoodsScreen(appViewModel = appViewModel, onBack = { navController.popBackStack() })
            }

            // DIET LOG SCREEN (New Feature)
            composable(AppDestinations.DIET_LOG_ROUTE) {
                DietLogScreen(onBack = { navController.popBackStack() })
            }

            // TIPS SCREEN (Animation)
            composable(AppDestinations.TIPS_ROUTE) {
                TipsScreen(onBack = { navController.popBackStack() })
            }

            // SETTINGS SCREEN (DataStore)
            composable(AppDestinations.SETTINGS_ROUTE) {
                SettingsScreen(appViewModel = appViewModel, onBack = { navController.popBackStack() })
            }
        }
    }
}


// Mock Factory for Preview (Cleaned and stable)
private val MockAppViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(
                foodService = FoodService.instance,
                dataStoreManager = DataStoreManager(null as Context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val mockViewModel: AppViewModel = viewModel(factory = MockAppViewModelFactory)

    NutriScalpTheme {
        HomeScreen(
            appViewModel = mockViewModel,
            onNavigate = {}
        )
    }
}