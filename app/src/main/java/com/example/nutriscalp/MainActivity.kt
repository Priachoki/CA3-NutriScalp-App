package com.example.nutriscalp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nutriscalp.data.DataStoreManager
import com.example.nutriscalp.data.FoodService
import com.example.nutriscalp.data.MealRepository
import com.example.nutriscalp.data.UserRepository
import com.example.nutriscalp.room.AppDatabase
import com.example.nutriscalp.ui.components.BottomNavigationBar
import com.example.nutriscalp.ui.components.BottomNavItem
import com.example.nutriscalp.ui.screens.DietLogScreen
import com.example.nutriscalp.ui.screens.FoodsScreen
import com.example.nutriscalp.ui.screens.SettingsScreen
import com.example.nutriscalp.ui.screens.TipsScreen
import com.example.nutriscalp.ui.screens.LoginScreen
import com.example.nutriscalp.ui.screens.SplashScreen
import com.example.nutriscalp.ui.screens.home.HomeScreen
import com.example.nutriscalp.ui.theme.NutriScalpTheme
import kotlinx.coroutines.flow.flowOf
import com.example.nutriscalp.room.MealDao
import com.example.nutriscalp.room.MealEntity
import com.example.nutriscalp.room.UserDao
import com.example.nutriscalp.room.UserEntity
import com.example.nutriscalp.ui.screens.FoodDetailScreen
import com.example.nutriscalp.ui.screens.MealHistoryScreen
import kotlinx.coroutines.flow.Flow


private lateinit var dataStoreManager: DataStoreManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        dataStoreManager = DataStoreManager(applicationContext)

        setContent {

            val database = AppDatabase.getDatabase(applicationContext)

            val mealRepository = MealRepository(database.mealDao())
            val userRepository = UserRepository(database.userDao())

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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = listOf(
        BottomNavItem(
            route = AppDestinations.HOME_ROUTE,
            icon = Icons.Default.Home,
            label = "Home"
        ),
        BottomNavItem(
            route = AppDestinations.FOODS_ROUTE,
            icon = Icons.Default.Fastfood,
            label = "Foods"
        ),
        BottomNavItem(
            route = AppDestinations.DIET_LOG_ROUTE,
            icon = Icons.Default.Today,
            label = "Log"
        ),
        BottomNavItem(
            route = AppDestinations.MEAL_HISTORY_ROUTE,
            icon = Icons.Default.ListAlt,
            label = "History"
        ),
        BottomNavItem(
            route = AppDestinations.TIPS_ROUTE,
            icon = Icons.Default.Lightbulb,
            label = "Tips"
        )
    )

    val shouldShowBottomNav = currentDestination?.route in bottomNavItems.map { it.route }

    NutriScalpTheme(appViewModel = appViewModel) {
        Scaffold(
            bottomBar = {
                if (shouldShowBottomNav) {
                    BottomNavigationBar(
                        navController = navController,
                        currentDestination = currentDestination,
                        items = bottomNavItems
                    )
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = AppDestinations.SPLASH_ROUTE,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(AppDestinations.SPLASH_ROUTE) {
                    SplashScreen(onNavigateToHome = {
                        navController.popBackStack()
                        navController.navigate(AppDestinations.LOGIN_ROUTE)
                    })
                }

                composable(AppDestinations.LOGIN_ROUTE) {
                    LoginScreen(
                        appViewModel = appViewModel,
                        onLoginSuccess = {

                            navController.navigate(AppDestinations.POST_LOGIN_SPLASH_ROUTE)
                        }
                    )
                }

                composable(AppDestinations.POST_LOGIN_SPLASH_ROUTE) {
                    SplashScreen(onNavigateToHome = {
                        navController.popBackStack(AppDestinations.LOGIN_ROUTE, inclusive = true)
                        navController.navigate(AppDestinations.HOME_ROUTE)
                    })
                }

                composable(AppDestinations.HOME_ROUTE) {
                    HomeScreen(
                        appViewModel = appViewModel,
                        onNavigate = { route ->

                            if (route == AppDestinations.SETTINGS_ROUTE) {
                                navController.navigate(route)
                            }
                        }
                    )
                }


                composable(AppDestinations.FOODS_ROUTE) {
                    FoodsScreen(
                        appViewModel = appViewModel,
                        onBack = { navController.popBackStack() },

                        onNavigateToDetail = { foodId ->
                            navController.navigate("${AppDestinations.FOOD_DETAIL_BASE_ROUTE}/$foodId")
                        }
                    )
                }

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

                composable(AppDestinations.DIET_LOG_ROUTE) {
                    DietLogScreen(appViewModel = appViewModel, onBack = { navController.popBackStack() })
                }

                composable(AppDestinations.MEAL_HISTORY_ROUTE) {
                    MealHistoryScreen(appViewModel = appViewModel, onBack = { navController.popBackStack() })
                }

                composable(AppDestinations.TIPS_ROUTE) {
                    TipsScreen(appViewModel = appViewModel, onBack = { navController.popBackStack() })
                }

                composable(AppDestinations.SETTINGS_ROUTE) {
                    SettingsScreen(
                        appViewModel = appViewModel,
                        onBack = { navController.popBackStack() },
                        onLogout = {
                            navController.popBackStack(route = AppDestinations.HOME_ROUTE, inclusive = true)
                            navController.navigate(AppDestinations.LOGIN_ROUTE)
                        }
                    )
                }
            }
        }
    }
}


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