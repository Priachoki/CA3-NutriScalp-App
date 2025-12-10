package com.example.nutriscalp.data

// Model for data retrieved from a mock network service
data class Food(
    val id: Int,
    val name: String,
    val imageRes: Int,
    val description: String,
    val scalpBenefit: String,
    val category: String,
    val nutrients: String,
    val howToEat: String,
    val bestPairings: String
)