package com.example.nutriscalp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val passwordHash: String, // Store a hash of the password
    val fullName: String? = null,

    val dryness: Int = 30,        // 0–100 (% dryness, default 30)
    val oiliness: Int = 60,       // 0–100 (% oiliness, default 60)
    val inflammation: Int = 0     // 0 = low 1= medium 2 High
)