package com.example.nutriscalp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutriscalp.R
import com.example.nutriscalp.ui.theme.LeafGreen
import com.example.nutriscalp.ui.theme.NutriScalpTheme
import com.example.nutriscalp.ui.theme.SoftCream

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    // Simple state for input fields (no actual validation needed for this step)
    var username by remember { mutableStateOf("user@example.com") }
    var password by remember { mutableStateOf("password") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftCream)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Logo
        Image(
            painter = painterResource(id = R.drawable.nutriscalp_logo),
            contentDescription = "NutriScalp Logo",
            modifier = Modifier.size(150.dp).padding(bottom = 32.dp)
        )

        Text("Login to NutriScalp", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // Username Field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LeafGreen,
                focusedLabelColor = LeafGreen,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LeafGreen,
                focusedLabelColor = LeafGreen,
            )
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Login Button (Triggers navigation)
        Button(
            onClick = onLoginSuccess, // Navigates to Home Screen
            modifier = Modifier.fillMaxWidth(0.6f).height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LeafGreen)
        ) {
            Text("LOGIN", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    NutriScalpTheme {
        LoginScreen(onLoginSuccess = {})
    }
}