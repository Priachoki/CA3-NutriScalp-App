package com.example.nutriscalp.data

import com.example.nutriscalp.room.MealDao
import com.example.nutriscalp.room.MealEntity
import kotlinx.coroutines.flow.Flow


class MealRepository(private val dao: MealDao) {

    fun getAllMeals(): Flow<List<MealEntity>> = dao.getAllMeals()

    suspend fun insertMeal(meal: MealEntity) = dao.insertMeal(meal)
}