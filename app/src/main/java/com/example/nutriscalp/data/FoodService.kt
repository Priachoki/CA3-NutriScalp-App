package com.example.nutriscalp.data

import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// --- Retrofit Interface (Mocked) ---
interface MockFoodApi {
    @GET("foods")
    suspend fun fetchFoods(): List<Food>
}

// --- Mocked Implementation of the Retrofit Service (Getting Data from Internet) ---
class FoodService private constructor() {

    private val mockApi = object : MockFoodApi {
        override suspend fun fetchFoods(): List<Food> {
            // Simulate network delay
            delay(1000)
            return mockFoodList
        }
    }

    // Mock Data
    private val mockFoodList = listOf(
        Food(1, "Spinach", "https://i.imgur.com/example_spinach.png", "Rich in iron and folate, essential for hair growth.", "Promotes hair follicle health and circulation."),
        Food(2, "Salmon", "https://i.imgur.com/example_salmon.png", "High in Omega-3 fatty acids.", "Reduces scalp inflammation and dryness."),
        Food(3, "Avocado", "https://i.imgur.com/example_avocado.png", "A good source of Vitamin E.", "Protects scalp skin from oxidative damage."),
        Food(4, "Sweet Potatoes", "https://i.imgur.com/example_potato.png", "Packed with Beta-Carotene.", "Aids in the production of sebum, a natural scalp oil.")
    )

    suspend fun getFoods(): List<Food> {
        return mockApi.fetchFoods()
    }

    companion object {
        val instance by lazy { FoodService() }
    }
}