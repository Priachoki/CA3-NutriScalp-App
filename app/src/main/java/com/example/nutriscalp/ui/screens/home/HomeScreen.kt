package com.example.nutriscalp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutriscalp.AppDestinations
import com.example.nutriscalp.AppViewModel
import com.example.nutriscalp.R
import com.example.nutriscalp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(appViewModel: AppViewModel, onNavigate: (String) -> Unit) {
    // Architecture Components: State Hoisting (collect state from ViewModel)
    val scalpScore by appViewModel.scalpScore.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { NutriScalpImageLogo() },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = SoftCream,
                    titleContentColor = TextDark
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .background(SoftCream)
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize()
        ) {

            // ===== SCORE CARD =====
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        "Today's Scalp Score",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Dynamic data from ViewModel
                    Text("Dryness: ${scalpScore.dryness}", fontSize = 16.sp, color = TextDark)
                    Text("Oiliness: ${scalpScore.oiliness}", fontSize = 16.sp, color = TextDark)
                    Text("Inflammation: ${scalpScore.inflammation}", fontSize = 16.sp, color = TextDark)
                }
            }

            Text(
                text = "Quick Access",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(20.dp))

            // FIRST ROW - Navigation to Foods and Diet Log (not implemented)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                // Navigates to FoodsScreen (Retrofit/Coil/LazyColumn)
                QuickButton(title = "Foods", onClick = { onNavigate(AppDestinations.FOODS_ROUTE) })
                QuickButton(title = "Diet Log", onClick = { /* TODO: Implement Diet Log Screen */ })
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SECOND ROW - Navigation to Tips and History/Settings
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                // Navigates to TipsScreen (Animation)
                QuickButton(title = "Tips", onClick = { onNavigate(AppDestinations.TIPS_ROUTE) })
                // Navigates to SettingsScreen (DataStore)
                QuickButton(title = "Settings", onClick = { onNavigate(AppDestinations.SETTINGS_ROUTE) })
            }
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

// QuickButton updated to accept an onClick action
@Composable
fun QuickButton(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(width = 150.dp, height = 100.dp)
            .clickable(onClick = onClick), // Use provided onClick
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark
            )
        }
    }
}