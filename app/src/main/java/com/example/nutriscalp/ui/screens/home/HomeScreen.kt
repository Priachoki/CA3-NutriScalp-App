package com.example.nutriscalp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutriscalp.R
import com.example.nutriscalp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
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

                    Text("Dryness: 30%", fontSize = 16.sp, color = TextDark)
                    Text("Oiliness: 60%", fontSize = 16.sp, color = TextDark)
                    Text("Inflammation: Low", fontSize = 16.sp, color = TextDark)
                }
            }

            Text(
                text = "Quick Access",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(20.dp))

            // FIRST ROW
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                QuickButton(title = "Foods")
                QuickButton(title = "Diet Log")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SECOND ROW
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                QuickButton(title = "Tips")
                QuickButton(title = "History")
            }
        }
    }
}

@Composable
fun NutriScalpImageLogo() {
    Image(
        painter = painterResource(id = R.drawable.nutriscalp_logo),
        contentDescription = null,
        modifier = Modifier
            .height(90.dp)
            .padding(top = 6.dp)
    )
}

@Composable
fun QuickButton(title: String) {
    Card(
        modifier = Modifier
            .size(width = 150.dp, height = 100.dp)
            .clickable {},
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
