package com.example.nutriscalp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriscalp.data.DataStoreManager
import com.example.nutriscalp.data.Food
import com.example.nutriscalp.data.FoodService
import com.example.nutriscalp.data.MealRepository
import com.example.nutriscalp.room.MealEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Data class to hold the static UI state
data class ScalpScore(
    val dryness: String = "30%",
    val oiliness: String = "60%",
    val inflammation: String = "Low"
)

class AppViewModel(
    private val foodService: FoodService,
    private val dataStoreManager: DataStoreManager,
    private val mealRepository: MealRepository
) : ViewModel() {

    // Architecture Components: State Flow (Static default)
    private val _scalpScore = MutableStateFlow(ScalpScore())
    val scalpScore: StateFlow<ScalpScore> = _scalpScore.asStateFlow()

    private val _foods = MutableStateFlow<List<Food>>(emptyList())
    val foods: StateFlow<List<Food>> = _foods.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _meals = MutableStateFlow<List<MealEntity>>(emptyList())
    val meals: StateFlow<List<MealEntity>> = _meals.asStateFlow()


    init {
        Log.d("NutriScalpApp", "AppViewModel initialized. Fetching initial data.")
        fetchFoods()
        loadPreferences()
        loadMeals()
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
            mealRepository: MealRepository
        ) = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return AppViewModel(
                        foodService,
                        dataStoreManager,
                        mealRepository
                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}