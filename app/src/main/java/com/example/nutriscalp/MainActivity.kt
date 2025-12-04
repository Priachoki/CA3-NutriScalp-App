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
import com.example.nutriscalp.ui.screens.FoodsScreen
import com.example.nutriscalp.ui.screens.SettingsScreen
import com.example.nutriscalp.ui.screens.TipsScreen
import com.example.nutriscalp.ui.screens.LoginScreen
import com.example.nutriscalp.ui.screens.SplashScreen
import com.example.nutriscalp.ui.screens.home.HomeScreen
import com.example.nutriscalp.ui.theme.NutriScalpTheme

// Initialization of DataStoreManager outside of a composable to be reused
private lateinit var dataStoreManager: DataStoreManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Initialize DataStoreManager using the application context
        dataStoreManager = DataStoreManager(applicationContext)

        setContent {
            // Instantiate the main application ViewModel using the factory
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

    NutriScalpTheme {
        NavHost(
            navController = navController,
            startDestination = AppDestinations.SPLASH_ROUTE // Start at the Splash Screen
        ) {
            // SPLASH SCREEN (Animation) -> LOGIN
            composable(AppDestinations.SPLASH_ROUTE) {
                SplashScreen(onNavigateToHome = {
                    // Navigate from Splash to Login
                    navController.popBackStack()
                    navController.navigate(AppDestinations.LOGIN_ROUTE)
                })
            }

            // LOGIN SCREEN -> HOME
            composable(AppDestinations.LOGIN_ROUTE) {
                LoginScreen(onLoginSuccess = {
                    // Navigate from Login to Home and clear the login screen from the back stack
                    navController.popBackStack()
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

            // FOODS SCREEN (LazyColumn, Retrofit, Coil)
            composable(AppDestinations.FOODS_ROUTE) {
                FoodsScreen(appViewModel = appViewModel, onBack = { navController.popBackStack() })
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


// -----------------------------------------------------------------------------
// HELPER FOR PREVIEW: Mock Factory to avoid complex Android Context issues
// -----------------------------------------------------------------------------

private val MockAppViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Instantiates the ViewModel, passing mock/minimal dependencies suitable for Preview
            return AppViewModel(
                foodService = FoodService.instance,
                // Cast null as Context to safely satisfy the non-nullable parameter
                dataStoreManager = DataStoreManager(null as Context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    // Correct usage of viewModel() with the mock factory for composable preview
    val mockViewModel: AppViewModel = viewModel(factory = MockAppViewModelFactory)

    NutriScalpTheme {
        HomeScreen(
            appViewModel = mockViewModel,
            onNavigate = {}
        )
    }
}