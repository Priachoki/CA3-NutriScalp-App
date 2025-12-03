package com.example.nutriscalp.data.repositories

import com.example.nutriscalp.data.local.dao.UserDao
import com.example.nutriscalp.data.local.entities.User
import kotlinx.coroutines.flow.Flow
import java.util.Date

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(email: String, username: String, password: String): Long {
        // In production: hash the password!
        val passwordHash = password // Use BCrypt or similar in production
        val user = User(
            email = email,
            username = username,
            passwordHash = passwordHash
        )
        return userDao.insertUser(user)
    }

    suspend fun login(email: String, password: String): User? {
        val passwordHash = password // Hash this in production
        return userDao.login(email, passwordHash)
    }

    suspend fun emailExists(email: String): Boolean {
        return userDao.emailExists(email) > 0
    }

    suspend fun updateLastLogin(userId: Long) {
        // You'd need to fetch, update, and save the user
    }
}