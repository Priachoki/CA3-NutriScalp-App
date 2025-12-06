package com.example.nutriscalp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class MealEntry(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val calories: Int,
    val notes: String
)

class MealPreferences(context: Context) {
    // Use SharedPreferences as a simpler alternative to Room DB
    private val prefs = context.getSharedPreferences("MealHistoryPrefs", Context.MODE_PRIVATE)
    private val MEAL_KEY = "meal_list"
    private val gson = Gson()

    // Save the entire list of meals to SharedPreferences
    suspend fun saveMeals(meals: List<MealEntry>) = withContext(Dispatchers.IO) {
        val json = gson.toJson(meals)
        prefs.edit().putString(MEAL_KEY, json).apply()
    }

    // Load the entire list of meals from SharedPreferences
    suspend fun loadMeals(): List<MealEntry> = withContext(Dispatchers.IO) {
        val json = prefs.getString(MEAL_KEY, null)
        return@withContext if (json != null) {
            val type = object : TypeToken<List<MealEntry>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } else {
            emptyList()
        }
    }
}