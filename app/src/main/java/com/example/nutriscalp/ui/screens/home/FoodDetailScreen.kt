package com.example.nutriscalp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nutriscalp.AppViewModel
import com.example.nutriscalp.ui.theme.AccentPrimary
import com.example.nutriscalp.ui.theme.AccentSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailScreen(appViewModel: AppViewModel, foodId: Int?, onBack: () -> Unit) {

    val food = foodId?.let { appViewModel.getFoodById(it) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(food?.name ?: "Food Details") },
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

        if (food == null) {
            Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Food item not found.", color = MaterialTheme.colorScheme.error)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            Image(
                painter = painterResource(id = food.imageRes),
                contentDescription = food.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(20.dp)) {

                Text(food.name, fontSize = 32.sp, fontWeight = FontWeight.Bold)

                Spacer(Modifier.height(16.dp))

                AssistChip(
                    onClick = {},
                    label = { Text(food.category) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = AccentSecondary.copy(0.2f),
                        labelColor = AccentPrimary
                    )
                )

                Spacer(Modifier.height(22.dp))

                DetailCard(title = "Scalp Benefit", text = food.scalpBenefit)

                Spacer(Modifier.height(20.dp))

                DetailCard(title = "Nutritional Highlights", text = food.nutrients)

                Spacer(Modifier.height(20.dp))

                DetailCard(title = "How to Consume", text = food.howToEat)

                Spacer(Modifier.height(20.dp))

                DetailCard(title = "Best Paired With", text = food.bestPairings)

                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun DetailCard(title: String, text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AccentSecondary.copy(0.12f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(Modifier.padding(18.dp)) {
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AccentPrimary)
            Spacer(Modifier.height(6.dp))
            Text(text, fontSize = 16.sp)
        }
    }
}
