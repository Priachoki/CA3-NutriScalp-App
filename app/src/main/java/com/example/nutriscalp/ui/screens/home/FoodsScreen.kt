package com.example.nutriscalp.ui.screens

import androidx.compose.foundation.Image
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
// 庁 IMPORTANT: Import ContentScale to use ContentScale.Crop
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nutriscalp.AppViewModel
import com.example.nutriscalp.data.Food
import com.example.nutriscalp.ui.theme.AccentPrimary
import com.example.nutriscalp.AppDestinations // 💡 NEW IMPORT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodsScreen(appViewModel: AppViewModel, onBack: () -> Unit, onNavigateToDetail: (Int) -> Unit) {

    val foods by appViewModel.foods.collectAsState()
    val scalp by appViewModel.scalpScore.collectAsState()

    // choose user scalp conditions based on their scores
    val userConditions = mutableListOf<String>()

    val drynessValue = scalp.dryness.removeSuffix("%").toIntOrNull() ?: 0
    val oilinessValue = scalp.oiliness.removeSuffix("%").toIntOrNull() ?: 0
    val inflamValue = scalp.inflammation

    if (drynessValue > 50) userConditions.add("Dryness")
    if (oilinessValue > 50) userConditions.add("Oiliness")
    if (inflamValue == "High") userConditions.add("Inflammation")

    // filter foods that match ANY of the user conditions
    val recommendedFoods = foods.filter { food ->
        userConditions.any { condition ->
            food.category.contains(condition, ignoreCase = true)
        }
    }

    val displayFoods = if (recommendedFoods.isNotEmpty()) {
        recommendedFoods
    } else {
        foods // show everything if nothing matches
    }



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
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
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
                    items(displayFoods) { food ->
                        FoodCard(food = food, onNavigateToDetail = onNavigateToDetail) // 💡 MODIFIED: Pass navigation lambda
                    }
                }
            }
        }
    }
}

// Food Card with "Large Image, Magazine-style" professional styling
@Composable
fun FoodCard(food: Food, onNavigateToDetail: (Int) -> Unit) { // 💡 MODIFIED: Added onNavigateToDetail
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), // Soft edges
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp) // Stronger elevation for big card
    ) {
        // 庁 MODIFIED: Changed from Row to Column to stack image and text
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToDetail(food.id) } // 💡 FIXED: Navigate to detail
        ) {
            Image(
                painter = painterResource(id = food.imageRes),
                contentDescription = food.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
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
                        color = MaterialTheme.colorScheme.onSurface, // 💡 FIXED: Uses dynamic text color for cards
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Benefit: ${food.scalpBenefit}",
                        fontSize = 14.sp,
                        color = AccentPrimary, // AccentPrimary does not change for this look
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