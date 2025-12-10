package com.example.nutriscalp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // Used for registration/seeding
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity): Long

    // Used for login validation: queries for a user with matching email and password hash
    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :passwordHash LIMIT 1")
    suspend fun getUserByCredentials(email: String, passwordHash: String): UserEntity?

    // Utility to check if a user already exists (for seeding/registration)
    @Query("SELECT COUNT(id) FROM users WHERE email = :email")
    suspend fun countUserByEmail(email: String): Int

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Int): UserEntity?}