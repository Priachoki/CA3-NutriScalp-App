package com.example.nutriscalp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 💡 UPDATE: Add UserEntity and increment version
@Database(entities = [MealEntity::class, UserEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao
    abstract fun userDao(): UserDao // 💡 NEW: Expose UserDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nutriscalp_database"
                )
                    // 💡 ADD: Allows database schema changes without writing migrations
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
    }
}