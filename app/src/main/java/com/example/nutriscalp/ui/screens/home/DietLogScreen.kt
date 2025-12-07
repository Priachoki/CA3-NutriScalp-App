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
import com.example.nutriscalp.ui.theme.AccentPrimary
import com.example.nutriscalp.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietLogScreen(appViewModel: AppViewModel, onBack: () -> Unit) { // 💡 MODIFIED: Added AppViewModel
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
                    containerColor = AccentPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Log a New Meal",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
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
                    focusedBorderColor = AccentPrimary,
                    focusedLabelColor = AccentPrimary,
                )
            )

            // Calories Input
            OutlinedTextField(
                value = calories,
                onValueChange = { calories = it.filter { it.isDigit() } }, // Only allow digits
                label = { Text("Calories (kcal)") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPrimary,
                    focusedLabelColor = AccentPrimary,
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
                    focusedBorderColor = AccentPrimary,
                    focusedLabelColor = AccentPrimary,
                )
            )

            // Save Button
            Button(
                // 💡 NEW LOGIC: Check inputs before saving
                onClick = {
                    val calCount = calories.toIntOrNull()
                    if (mealName.isNotBlank() && calCount != null && calCount > 0) {
                        appViewModel.saveMeal(mealName, calCount, notes)
                        onBack()
                    }
                    // In a real app, you would show a snackbar for validation errors
                },
                enabled = mealName.isNotBlank() && calories.toIntOrNull() != null,
                modifier = Modifier.fillMaxWidth(0.6f).height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentPrimary)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Meal")
                Spacer(Modifier.width(8.dp))
                Text("SAVE MEAL", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}