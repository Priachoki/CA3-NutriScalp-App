package com.example.nutriscalp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriscalp.data.DataStoreManager
import com.example.nutriscalp.data.Food
import com.example.nutriscalp.data.FoodService
import com.example.nutriscalp.data.MealRepository
import com.example.nutriscalp.data.UserRepository
import com.example.nutriscalp.room.MealEntity
import com.example.nutriscalp.room.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted // 💡 NEW IMPORT
import kotlinx.coroutines.flow.stateIn // 💡 NEW IMPORT
import java.util.Calendar // 💡 FIXED: Use Calendar for API 24 compatibility

// Data class to hold the static UI state
data class ScalpScore(
    val dryness: String = "30%",
    val oiliness: String = "60%",
    val inflammation: String = "Low"
)

class AppViewModel(
    private val foodService: FoodService,
    private val dataStoreManager: DataStoreManager,
    private val mealRepository: MealRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    // Architecture Components: State Flow (Static default)
    private val _scalpScore = MutableStateFlow(ScalpScore())
    val scalpScore: StateFlow<ScalpScore> = _scalpScore.asStateFlow()

    private var currentUser: UserEntity? = null
    private val _foods = MutableStateFlow<List<Food>>(emptyList())
    val foods: StateFlow<List<Food>> = _foods.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _meals = MutableStateFlow<List<MealEntity>>(emptyList())
    val meals: StateFlow<List<MealEntity>> = _meals.asStateFlow()

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    // Helper function to check if a timestamp falls on today's date (API 24 safe)
    private fun isToday(timestamp: Long): Boolean {
        // Get the start of today's date in milliseconds
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfToday = calendar.timeInMillis

        // Get the start of tomorrow's date
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val startOfTomorrow = calendar.timeInMillis

        // Check if the timestamp is between the start of today and the start of tomorrow
        return timestamp >= startOfToday && timestamp < startOfTomorrow
    }

    // 💡 FIXED: Use stateIn to convert Flow to StateFlow with an initial value, and use isToday()
    val todayCalories: StateFlow<Int> = meals.map { mealsList ->
        mealsList.filter { meal ->
            isToday(meal.timestamp)
        }.sumOf { it.calories }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // Keep the flow active while UI is visible
        initialValue = 0 // Initial value
    )


    init {
        Log.d("NutriScalpApp", "AppViewModel initialized. Fetching initial data.")
        fetchFoods()
        loadPreferences()
        loadMeals()
        seedDatabase()
    }

    // 💡 Function to get a single Food object by ID
    fun getFoodById(foodId: Int): Food? {
        return _foods.value.find { it.id == foodId }
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            // In a real app, you would clear user tokens/DataStore prefs here.
            _isUserLoggedIn.value = false
            onLogoutSuccess()
            Log.i("NutriScalpApp", "User logged out.")
        }
    }

    private fun seedDatabase() {
        viewModelScope.launch {

            // ALL demo users share this hashed password ("password")
            val HASH = "password_hash_1234"

            val demoUsers = listOf(
                UserEntity(
                    email = "test@nutriscalp.com",
                    passwordHash = HASH,
                    fullName = "NutriScalp Tester",
                    dryness = 70,
                    oiliness = 20,
                    inflammation = 1
                ),
                UserEntity(
                    email = "dryness@nutriscalp.com",
                    passwordHash = HASH,
                    fullName = "Dryness User",
                    dryness = 90,
                    oiliness = 10,
                    inflammation = 0
                ),
                UserEntity(
                    email = "oil@nutriscalp.com",
                    passwordHash = HASH,
                    fullName = "Oily User",
                    dryness = 10,
                    oiliness = 90,
                    inflammation = 0
                ),
                UserEntity(
                    email = "inflammation@nutriscalp.com",
                    passwordHash = HASH,
                    fullName = "Inflammation User",
                    dryness = 20,
                    oiliness = 20,
                    inflammation = 2
                ),
                UserEntity(
                    email = "mixed@nutriscalp.com",
                    passwordHash = HASH,
                    fullName = "Mixed Scalp User",
                    dryness = 60,
                    oiliness = 70,
                    inflammation = 2
                ),
                UserEntity(
                    email = "new@nutriscalp.com",
                    passwordHash = HASH,
                    fullName = "New User",
                    dryness = 0,
                    oiliness = 0,
                    inflammation = 0
                )
            )

            demoUsers.forEach { user ->
                if (!userRepository.userExists(user.email)) {
                    userRepository.insertUser(user)
                    Log.d("NutriScalpApp", "Seeded user: ${user.email}")
                }
            }
        }
    }


    fun loginUser(email: String, passwordHash: String, onLoginSuccess: () -> Unit, onLoginFailure: () -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserByCredentials(email, passwordHash)
            if (user != null) {
                currentUser = user
                loadUserScalpData(user)

                _isUserLoggedIn.value = true
                onLoginSuccess()
                Log.i("NutriScalpApp", "User logged in: ${user.email}")
            } else {
                _isUserLoggedIn.value = false
                onLoginFailure()
                Log.w("NutriScalpApp", "Login failed for email: $email")
            }
        }
    }

    private fun loadUserScalpData(user: UserEntity){
        _scalpScore.value = ScalpScore(
           dryness = "${user.dryness}%",
            oiliness = "${user.oiliness}%",
            inflammation = when (user.inflammation){
                2 -> "High"
                1 -> "Medium"
                else -> "Low"
            }
        )
        Log.d("NutriScalpApp", "Loaded scalp profile for ${user.email}")
    }
    // Getting Data from Internet (mocked)
    private fun fetchFoods() {
        viewModelScope.launch {
            try {
                // Retrofit mock call
                val fetchedFoods = foodService.getFoods()
                _foods.value = fetchedFoods
            } catch (e: Exception) {
                Log.e("NutriScalpApp", "Error fetching foods: ${e.message}")
                _foods.value = emptyList()
            }
        }
    }

    // Use DataStore
    private fun loadPreferences() {
        viewModelScope.launch {
            _isDarkMode.value = dataStoreManager.isDarkMode.first()
        }
    }

    private fun loadMeals() {
        viewModelScope.launch {
            mealRepository.getAllMeals().collect { list ->
                _meals.value = list
            }
        }
    }

    // MODIFIED: Meal save is now exposed to the UI
    fun saveMeal(mealName: String, calories: Int, notes: String) {
        viewModelScope.launch {
            val meal = MealEntity(
                mealName = mealName,
                calories = calories,
                notes = notes
            )
            mealRepository.insertMeal(meal)
        }
    }

    // Use DataStore
    fun toggleDarkMode(enable: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setDarkMode(enable)
            _isDarkMode.value = enable
        }
    }

    // Custom ViewModel Factory
    companion object {
        fun factory(
            foodService: FoodService,
            dataStoreManager: DataStoreManager,
            mealRepository: MealRepository,
            userRepository: UserRepository
        ) = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return AppViewModel(
                        foodService,
                        dataStoreManager,
                        mealRepository,
                        userRepository
                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}