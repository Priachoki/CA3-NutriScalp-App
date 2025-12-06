package com.example.nutriscalp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nutriscalp.ui.theme.AccentPrimary // 💡 NEW
import com.example.nutriscalp.ui.theme.BackgroundLight // 💡 NEW

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietLogScreen(onBack: () -> Unit) {
    var mealName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Diet Log") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AccentPrimary, // 💡 Changed from AccentTerra
                    titleContentColor = BackgroundLight, // 💡 Changed from PureCream
                    navigationIconContentColor = BackgroundLight // 💡 Changed from PureCream
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(BackgroundLight) // 💡 Changed from PureCream
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Log a New Meal",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Meal Name Input
            OutlinedTextField(
                value = mealName,
                onValueChange = { mealName = it },
                label = { Text("Meal / Food Item") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPrimary, // 💡 Changed from AccentTerra
                    focusedLabelColor = AccentPrimary, // 💡 Changed from AccentTerra
                )
            )

            // Calories Input
            OutlinedTextField(
                value = calories,
                onValueChange = { calories = it },
                label = { Text("Calories (kcal)") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions( // Corrected usage
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPrimary, // 💡 Changed from AccentTerra
                    focusedLabelColor = AccentPrimary, // 💡 Changed from AccentTerra
                )
            )

            // Notes Area
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Scalp Observations/Notes") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(150.dp).padding(bottom = 32.dp),
                maxLines = 5,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPrimary, // 💡 Changed from AccentTerra
                    focusedLabelColor = AccentPrimary, // 💡 Changed from AccentTerra
                )
            )

            // Save Button
            Button(
                onClick = {
                    // Navigate back after logging the meal
                    onBack()
                },
                modifier = Modifier.fillMaxWidth(0.6f).height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentPrimary) // 💡 Changed from AccentTerra
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Meal")
                Spacer(Modifier.width(8.dp))
                Text("SAVE MEAL", fontWeight = FontWeight.Bold)
            }
        }
    }
}