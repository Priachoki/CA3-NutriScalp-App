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
// 💡 IMPORTANT: Import ContentScale to use ContentScale.Crop
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nutriscalp.AppViewModel
import com.example.nutriscalp.data.Food
// 💡 UPDATED IMPORTS (Using new Deep Teal/Aqua color scheme)
import com.example.nutriscalp.ui.theme.AccentPrimary
import com.example.nutriscalp.ui.theme.BackgroundLight
import com.example.nutriscalp.ui.theme.TextDark

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
                    containerColor = AccentPrimary,
                    titleContentColor = BackgroundLight,
                    navigationIconContentColor = BackgroundLight
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(BackgroundLight)
                .padding(padding)
                .fillMaxSize()
        ) {
            if (foods.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AccentPrimary)
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

// Food Card with "Large Image, Magazine-style" professional styling
@Composable
fun FoodCard(food: Food) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), // Soft edges
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp) // Stronger elevation for big card
    ) {
        // 💡 MODIFIED: Changed from Row to Column to stack image and text
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* TBD: Add navigation to detail screen */ }
        ) {
            // 💡 LARGE IMAGE IMPLEMENTATION
            AsyncImage(
                model = food.imageUrl,
                contentDescription = "${food.name} image",
                modifier = Modifier
                    .fillMaxWidth() // Image spans the full width of the card
                    .height(180.dp) // Big, fixed height
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), // Clip top corners only
                contentScale = ContentScale.Crop // Crop to fill the space
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // Padding applied to the text area
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        food.name,
                        fontSize = 20.sp, // Slightly larger title
                        color = TextDark,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Benefit: ${food.scalpBenefit}",
                        fontSize = 14.sp,
                        color = AccentPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
                // Detail Arrow icon (kept small)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Details",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}