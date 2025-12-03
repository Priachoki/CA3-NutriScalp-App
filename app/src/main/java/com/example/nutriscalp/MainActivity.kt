package com.example.nutriscalp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nutriscalp.ui.navigation.NutriScalpNavGraph  // Add this import
import com.example.nutriscalp.ui.theme.NutriScalpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriScalpApp()
        }
    }
}

@Composable
fun NutriScalpApp() {
    NutriScalpTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NutriScalpNavGraph()  // Use navigation graph
        }
    }
}