package com.example.nutriscalp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nutriscalp.AppViewModel
import com.example.nutriscalp.data.Food
// 🚨 UPDATED IMPORTS
import com.example.nutriscalp.ui.theme.AccentTerra // New Accent Color
import com.example.nutriscalp.ui.theme.PureCream // New Background/Light Color
import com.example.nutriscalp.ui.theme.TextDark // Text Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodsScreen(appViewModel: AppViewModel, onBack: () -> Unit) {
    // Retrofit: Collect food data fetched via ViewModel
    val foods by appViewModel.foods.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scalp Nutrition Sources") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AccentTerra, // 🎨 Changed from LeafGreen
                    titleContentColor = PureCream, // 🎨 Changed from SoftCream
                    navigationIconContentColor = PureCream // 🎨 Changed from SoftCream
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(PureCream) // 🎨 Changed from SoftCream
                .padding(padding)
                .fillMaxSize()
        ) {
            if (foods.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AccentTerra) // 🎨 Changed from LeafGreen
                }
            } else {
                // Lazy List with Card UI components
                LazyColumn(
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(foods) { food ->
                        FoodCard(food = food)
                    }
                }
            }
        }
    }
}

// Food Card with "Simplistic, Professional" styling (Coil implementation)
@Composable
fun FoodCard(food: Food) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), // Soft edges
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp) // Subtle elevation
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .clickable { /* TBD: Add navigation to detail screen */ },
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Load and Display Images using Coil
            AsyncImage(
                model = food.imageUrl,
                contentDescription = "${food.name} image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp)), // Rounded corners for image
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    food.name,
                    fontSize = 17.sp,
                    color = TextDark,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "Benefit: ${food.scalpBenefit}",
                    fontSize = 13.sp,
                    color = AccentTerra, // 🎨 Changed from LeafGreen
                    fontWeight = FontWeight.Medium
                )
            }
            // Detail Arrow icon
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Details",
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}