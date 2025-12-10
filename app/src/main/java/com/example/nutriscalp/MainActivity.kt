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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nutriscalp.data.DataStoreManager
import com.example.nutriscalp.data.FoodService
import com.example.nutriscalp.data.MealRepository
import com.example.nutriscalp.data.UserRepository
import com.example.nutriscalp.room.AppDatabase
import com.example.nutriscalp.ui.screens.DietLogScreen
import com.example.nutriscalp.ui.screens.FoodsScreen
import com.example.nutriscalp.ui.screens.SettingsScreen
import com.example.nutriscalp.ui.screens.TipsScreen
import com.example.nutriscalp.ui.screens.LoginScreen
import com.example.nutriscalp.ui.screens.SplashScreen
import com.example.nutriscalp.ui.screens.home.HomeScreen
import com.example.nutriscalp.ui.theme.NutriScalpTheme
import kotlinx.coroutines.flow.flowOf
// 🚨 NEW IMPORTS FOR MOCKING
import com.example.nutriscalp.room.MealDao
import com.example.nutriscalp.room.MealEntity
import com.example.nutriscalp.room.UserDao
import com.example.nutriscalp.room.UserEntity
import com.example.nutriscalp.ui.screens.FoodDetailScreen // 💡 NEW IMPORT
import com.example.nutriscalp.ui.screens.MealHistoryScreen // 💡 NEW IMPORT
import kotlinx.coroutines.flow.Flow


// Initialization of DataStoreManager outside of a composable to be reused
private lateinit var dataStoreManager: DataStoreManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        dataStoreManager = DataStoreManager(applicationContext)

        setContent {
            // Architecture Components: ViewModel instantiation

            val database = AppDatabase.getDatabase(applicationContext)

            val mealRepository = MealRepository(database.mealDao())
            val userRepository = UserRepository(database.userDao()) // Added from last step

            val appViewModel: AppViewModel = viewModel(
                factory = AppViewModel.factory(
                    foodService = FoodService.instance,
                    dataStoreManager = dataStoreManager,
                    mealRepository = mealRepository,
                    userRepository = userRepository
                )
            )

            NutriScalpApp(appViewModel)
        }
    }
}

@Composable
fun NutriScalpApp(appViewModel: AppViewModel) {
    val navController = rememberNavController()
    // Navigation between Screens
    NutriScalpTheme(appViewModel = appViewModel) {
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
                LoginScreen(
                    appViewModel = appViewModel,
                    onLoginSuccess = {
                        // Navigate to HOME and clear all previous screens (Splash/Login)
                        navController.popBackStack(AppDestinations.LOGIN_ROUTE, inclusive = true)
                        navController.navigate(AppDestinations.HOME_ROUTE)
                    }
                )
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
                FoodsScreen(
                    appViewModel = appViewModel,
                    onBack = { navController.popBackStack() },
                    // 💡 NEW NAVIGATION for Food Detail
                    onNavigateToDetail = { foodId ->
                        navController.navigate("${AppDestinations.FOOD_DETAIL_BASE_ROUTE}/$foodId")
                    }
                )
            }

            // FOOD DETAIL SCREEN 💡 NEW ROUTE WITH ARGUMENT
            composable(
                route = AppDestinations.FOOD_DETAIL_ROUTE,
                arguments = listOf(navArgument("foodId") { type = NavType.IntType })
            ) { backStackEntry ->
                val foodId = backStackEntry.arguments?.getInt("foodId")
                FoodDetailScreen(
                    appViewModel = appViewModel,
                    foodId = foodId,
                    onBack = { navController.popBackStack() }
                )
            }

            // DIET LOG SCREEN (New Feature)
            composable(AppDestinations.DIET_LOG_ROUTE) {
                DietLogScreen(appViewModel = appViewModel, onBack = { navController.popBackStack() }) // 💡 MODIFIED
            }

            // MEAL HISTORY SCREEN 💡 NEW ROUTE
            composable(AppDestinations.MEAL_HISTORY_ROUTE) {
                MealHistoryScreen(appViewModel = appViewModel, onBack = { navController.popBackStack() })
            }


            // TIPS SCREEN (Animation)
            composable(AppDestinations.TIPS_ROUTE) {
                TipsScreen(appViewModel = appViewModel, onBack = { navController.popBackStack() })
            }

            // SETTINGS SCREEN (DataStore)
            composable(AppDestinations.SETTINGS_ROUTE) {
                SettingsScreen(
                    appViewModel = appViewModel,
                    onBack = { navController.popBackStack() },
                    // 💡 NEW LOGOUT LOGIC: Clear back stack and navigate to LOGIN
                    onLogout = {
                        navController.popBackStack(route = AppDestinations.HOME_ROUTE, inclusive = true)
                        navController.navigate(AppDestinations.LOGIN_ROUTE)
                    }
                )
            }
        }
    }
}


// 🚨 NEW MOCK IMPLEMENTATIONS FOR PREVIEW FACTORY
private val MockMealDao = object : MealDao {
    override suspend fun insertMeal(meal: MealEntity) { /* No-op for preview */ }
    // Return an empty flow for preview
    override fun getAllMeals(): Flow<List<MealEntity>> = flowOf(emptyList())
}

private val MockUserDao = object : UserDao {

    override suspend fun insertUser(user: UserEntity): Long = 0L

    override suspend fun getUserByCredentials(
        email: String,
        passwordHash: String
    ): UserEntity? = null

    override suspend fun countUserByEmail(email: String): Int = 0

    override suspend fun updateUser(user: UserEntity) { /* no-op */ }

    override suspend fun getUserById(userId: Int): UserEntity? = null
}



private val MockMealRepository = MealRepository(MockMealDao)
private val MockUserRepository = UserRepository(MockUserDao)


// Mock Factory for Preview (Cleaned and stable)
private val MockAppViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(
                foodService = FoodService.instance,
                dataStoreManager = DataStoreManager(null as Context),
                mealRepository = MockMealRepository,
                userRepository = MockUserRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val mockViewModel: AppViewModel = viewModel(factory = MockAppViewModelFactory)

    NutriScalpTheme(appViewModel = mockViewModel) {
        HomeScreen(
            appViewModel = mockViewModel,
            onNavigate = {}
        )
    }
}