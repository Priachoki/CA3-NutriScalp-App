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
import com.example.nutriscalp.AppViewModel
import com.example.nutriscalp.ui.theme.AccentPrimary
import com.example.nutriscalp.ui.theme.NutriScalpTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(appViewModel: AppViewModel, onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("test@nutriscalp.com") }
    var password by remember { mutableStateOf("password") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope() // FIX: Get CoroutineScope for Snackbar

    val DUMMY_HASH = "password_hash_1234"
    val DUMMY_EMAIL = "test@nutriscalp.com"

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(horizontal = 32.dp, vertical = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Image(
                painter = painterResource(id = R.drawable.nutriscalp_logo),
                contentDescription = "NutriScalp Logo",
                modifier = Modifier.size(120.dp).padding(bottom = 16.dp)
            )

            Text(
                "Welcome Back",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground // 💡 FIXED
            )
            Text(
                "Log in to find your perfect scalp solution.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Email Address (Hint: $DUMMY_EMAIL)") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPrimary,
                    focusedLabelColor = AccentPrimary,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password (Hint: password)") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPrimary,
                    focusedLabelColor = AccentPrimary,
                )
            )
            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    val inputHash = if (password == "password") DUMMY_HASH else password

                    appViewModel.loginUser(
                        email = username,
                        passwordHash = inputHash,
                        onLoginSuccess = onLoginSuccess,
                        onLoginFailure = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Login failed. Check your credentials.",
                                    withDismissAction = true
                                )
                            }
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentPrimary)
            ) {
                Text("LOGIN", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary) // 💡 FIXED
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    val context = androidx.compose.ui.platform.LocalContext.current

    val mockMealDao = object : com.example.nutriscalp.room.MealDao {
        override suspend fun insertMeal(meal: com.example.nutriscalp.room.MealEntity) {}
        override fun getAllMeals() =
            kotlinx.coroutines.flow.flowOf(emptyList<com.example.nutriscalp.room.MealEntity>())
    }

    val mockUserDao = object : com.example.nutriscalp.room.UserDao {
        override suspend fun insertUser(user: com.example.nutriscalp.room.UserEntity) = 0L
        override suspend fun getUserByCredentials(email: String, passwordHash: String) = null
        override suspend fun countUserByEmail(email: String) = 0
        override suspend fun updateUser(user: com.example.nutriscalp.room.UserEntity) {}
        override suspend fun getUserById(userId: Int) = null
    }

    val previewFactory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AppViewModel(
                    foodService = com.example.nutriscalp.data.FoodService.instance,
                    dataStoreManager = com.example.nutriscalp.data.DataStoreManager(context),
                    mealRepository = com.example.nutriscalp.data.MealRepository(mockMealDao),
                    userRepository = com.example.nutriscalp.data.UserRepository(mockUserDao)
                ) as T
            }
            throw IllegalArgumentException("Unknown VM class")
        }
    }

    val mockViewModel: AppViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = previewFactory
    )

    NutriScalpTheme(appViewModel = mockViewModel) {
        LoginScreen(
            appViewModel = mockViewModel,
            onLoginSuccess = {}
        )
    }
}
