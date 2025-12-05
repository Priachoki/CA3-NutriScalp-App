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
// 🚨 UPDATED IMPORTS
import com.example.nutriscalp.ui.theme.AccentTerra
import com.example.nutriscalp.ui.theme.NutriScalpTheme
import com.example.nutriscalp.ui.theme.PureCream
import com.example.nutriscalp.ui.theme.TextDark

// Simplified Login Screen (Professional, Minimalist design)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("user@nutriscalp.com") }
    var password by remember { mutableStateOf("******") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PureCream) // 🎨 Changed from SoftCream
            .padding(horizontal = 32.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // Logo
        Image(
            painter = painterResource(id = R.drawable.nutriscalp_logo),
            contentDescription = "NutriScalp Logo",
            modifier = Modifier.size(120.dp).padding(bottom = 16.dp)
        )

        Text(
            "Welcome Back",
            style = MaterialTheme.typography.headlineMedium,
            color = TextDark
        )
        Text(
            "Log in to find your perfect scalp solution.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Username Field (Simplistic Outlined style)
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Email Address") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentTerra, // 🎨 Changed from LeafGreen
                focusedLabelColor = AccentTerra, // 🎨 Changed from LeafGreen
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
                focusedBorderColor = AccentTerra, // 🎨 Changed from LeafGreen
                focusedLabelColor = AccentTerra, // 🎨 Changed from LeafGreen
            )
        )
        Spacer(modifier = Modifier.height(48.dp))

        // Login Button (Primary Action)
        Button(
            onClick = onLoginSuccess,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentTerra) // 🎨 Changed from LeafGreen
        ) {
            Text("LOGIN", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
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