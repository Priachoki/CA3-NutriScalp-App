package com.example.nutriscalp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriscalp.data.DataStoreManager
import com.example.nutriscalp.data.Food
import com.example.nutriscalp.data.FoodService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Data class to hold the UI state for the Home Screen
data class ScalpScore(
    val dryness: String = "30%",
    val oiliness: String = "60%",
    val inflammation: String = "Low"
)

class AppViewModel(
    private val foodService: FoodService,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    // Architecture Components: State Flow for Scalp Score
    private val _scalpScore = MutableStateFlow(ScalpScore())
    val scalpScore: StateFlow<ScalpScore> = _scalpScore.asStateFlow()

    // State Flow for Foods List (Retrofit/Coil)
    private val _foods = MutableStateFlow<List<Food>>(emptyList())
    val foods: StateFlow<List<Food>> = _foods.asStateFlow()

    // State Flow for Dark Mode Preference (DataStore)
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    init {
        // Logging: Initial log message
        Log.d("NutriScalpApp", "AppViewModel initialized. Fetching initial data.")
        fetchFoods()
        loadPreferences()
    }

    // Getting Data from Internet using Retrofit
    private fun fetchFoods() {
        viewModelScope.launch {
            try {
                // Mock API call
                val fetchedFoods = foodService.getFoods()
                _foods.value = fetchedFoods
            } catch (e: Exception) {
                // Logging: Log error on network failure
                Log.e("NutriScalpApp", "Error fetching foods: ${e.message}")
                // In a real app, handle the error gracefully for the user
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

    fun toggleDarkMode(enable: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setDarkMode(enable)
            _isDarkMode.value = enable
        }
    }

    // Custom ViewModel Factory (Needed for passing dependencies)
    companion object {
        fun factory(foodService: FoodService, dataStoreManager: DataStoreManager) = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return AppViewModel(foodService, dataStoreManager) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}