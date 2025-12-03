package com.example.nutriscalp.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nutriscalp.R
import com.example.nutriscalp.ui.theme.SoftCream
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftCream),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.nutriscalp_logo),
            contentDescription = "NutriScalp Logo",
            modifier = Modifier.size(250.dp)
        )

        LaunchedEffect(key1 = true) {
            delay(2000)
            navController.navigate("home")
        }
    }
}