package com.example.nutriscalp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nutriscalp.AppViewModel
import com.example.nutriscalp.data.Food
import com.example.nutriscalp.ui.theme.LeafGreen
import com.example.nutriscalp.ui.theme.SoftCream
import com.example.nutriscalp.ui.theme.TextDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodsScreen(appViewModel: AppViewModel, onBack: () -> Unit) {
    // Collect food data fetched via Retrofit (in ViewModel)
    val foods by appViewModel.foods.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NutriScalp Foods") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LeafGreen,
                    titleContentColor = SoftCream,
                    navigationIconContentColor = SoftCream
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(SoftCream)
                .padding(padding)
                .fillMaxSize()
        ) {
            if (foods.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    // Show loading or error message
                    CircularProgressIndicator(color = LeafGreen)
                }
            } else {
                // Scrollable List (LazyColumn)
                LazyColumn(
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Lazy
                    items(foods) { food ->
                        FoodCard(food = food)
                    }
                }
            }
        }
    }
}

@Composable
fun FoodCard(food: Food) {
    // Card UI component
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Load and Display Images using Coil
            AsyncImage(
                model = food.imageUrl,
                contentDescription = "${food.name} image",
                modifier = Modifier.size(80.dp).fillMaxHeight().aspectRatio(1f),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
            Column {
                Text(food.name, fontSize = 18.sp, color = LeafGreen, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text("Benefits: ${food.scalpBenefit}", fontSize = 14.sp, color = TextDark)
                Spacer(Modifier.height(4.dp))
                Text(food.description, fontSize = 12.sp, color = TextDark)
            }
        }
    }
}