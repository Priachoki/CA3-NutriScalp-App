package com.example.nutriscalp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val email: String,
    val username: String,
    val passwordHash: String,
    // CHANGE 1: Use Long (timestamp) for storage
    val createdAt: Long = System.currentTimeMillis(),
    // CHANGE 2: Use Long? (nullable timestamp) for storage
    val lastLogin: Long? = null,
    val profileImage: String? = null
)