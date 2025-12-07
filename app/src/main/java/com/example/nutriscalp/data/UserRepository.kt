package com.example.nutriscalp.data

import com.example.nutriscalp.room.UserDao
import com.example.nutriscalp.room.UserEntity

class UserRepository(private val dao: UserDao) {

    suspend fun getUserByCredentials(email: String, passwordHash: String): UserEntity? {
        return dao.getUserByCredentials(email, passwordHash)
    }

    suspend fun insertUser(user: UserEntity): Long {
        return dao.insertUser(user)
    }

    suspend fun userExists(email: String): Boolean {
        return dao.countUserByEmail(email) > 0
    }
}