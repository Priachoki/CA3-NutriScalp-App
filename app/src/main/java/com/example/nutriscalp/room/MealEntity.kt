package com.example.nutriscalp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mealName: String,
    val calories: Int,
    val notes: String,
    val timestamp: Long = System.currentTimeMillis()
)