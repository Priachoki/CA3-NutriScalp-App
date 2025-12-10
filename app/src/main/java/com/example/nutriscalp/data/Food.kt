package com.example.nutriscalp.data

// Model for data retrieved from a mock network service
data class Food(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val scalpBenefit: String,
    val category: String
)