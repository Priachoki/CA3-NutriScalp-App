package com.example.nutriscalp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutriscalp.AppDestinations
import com.example.nutriscalp.AppViewModel
import com.example.nutriscalp.R
import com.example.nutriscalp.ui.theme.AccentPrimary
import com.example.nutriscalp.ui.theme.AccentSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(appViewModel: AppViewModel, onNavigate: (String) -> Unit) {
    val scalpScore by appViewModel.scalpScore.collectAsState()
    val todayCalories by appViewModel.todayCalories.collectAsState(initial = 0) // 💡 NEW: Collect today's calories

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { NutriScalpImageLogo() },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { padding ->

         Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(horizontal = 20.dp)
                .padding(bottom = 80.dp)
                .fillMaxSize()
        ) {
            // ===== WELCOME HEADER =====
            UserHeader(userName = "Vanessa")
            Spacer(modifier = Modifier.height(20.dp))

            // ===== SCORE CARD (UPDATED WITH CALORIE STAT) =====
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Today's Health Summary", // 💡 MODIFIED TITLE
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentSecondary,
                            modifier = Modifier.weight(1f)
                        )
                        // Logo Icon in the Score Card
                        Icon(
                            painter = painterResource(id = R.drawable.nutriscalp_logo),
                            contentDescription = "Scalp Health Icon",
                            tint = AccentPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Calories Logged: $todayCalories kcal", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold) // 💡 NEW DATA
                    Text("Dryness: ${scalpScore.dryness}", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                    Text("Oiliness: ${scalpScore.oiliness}", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                    Text("Inflammation: ${scalpScore.inflammation}", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)

                    // 💡 Simple Visualization Placeholder
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Meal History Trend (Mock Chart)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    // You would place a custom drawing Canvas or charting library component here
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(AccentSecondary.copy(alpha = 0.1f))
                        .padding(8.dp)
                    ) {
                        Text("Chart placeholder - Visualize 7-day calorie trend here.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                    }
                }
            }

            Text(
                text = "Quick Access",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(20.dp))

            // QUICK BUTTONS
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                QuickButton(
                    title = "Foods",
                    icon = Icons.Default.Fastfood,
                    onClick = { onNavigate(AppDestinations.FOODS_ROUTE) }
                )
                QuickButton(
                    title = "Diet Log",
                    icon = Icons.Default.ListAlt,
                    onClick = { onNavigate(AppDestinations.DIET_LOG_ROUTE) }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                QuickButton(
                    title = "Tips",
                    icon = Icons.Default.Lightbulb,
                    onClick = { onNavigate(AppDestinations.TIPS_ROUTE) }
                )
                // 💡 ADDED MEAL HISTORY BUTTON
                QuickButton(
                    title = "History",
                    icon = Icons.Default.ListAlt,
                    onClick = { onNavigate(AppDestinations.MEAL_HISTORY_ROUTE) }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Replaced old Diet Log Quick Button for Settings
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                QuickButton(
                    title = "Settings",
                    icon = Icons.Default.Settings,
                    onClick = { onNavigate(AppDestinations.SETTINGS_ROUTE) }
                )
            }
        }
    }
}

// ... UserHeader, NutriScalpImageLogo, QuickButton remain the same ...
// Omitting these functions for brevity, assume they are included in the file.
@Composable
fun UserHeader(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "Hello, $userName",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "Your wellness journey starts here",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.outline
            )
        }
        // Placeholder Profile Image (Using the NutriScalp theme colors)
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(AccentSecondary.copy(alpha = 0.5f))
                .clickable { /* TBD: Go to profile */ },
            contentAlignment = Alignment.Center
        ) {
            Text(userName.first().toString(), fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun NutriScalpImageLogo() {
    Image(
        painter = painterResource(id = R.drawable.nutriscalp_logo),
        contentDescription = "NutriScalp Logo",
        modifier = Modifier
            .height(90.dp)
            .padding(top = 6.dp)
    )
}

@Composable
fun QuickButton(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(width = 150.dp, height = 100.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = AccentPrimary,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}