package com.example.nutriscalp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
// 🚨 UPDATED IMPORTS
import com.example.nutriscalp.ui.theme.AccentTerra
import com.example.nutriscalp.ui.theme.PureCream
import com.example.nutriscalp.ui.theme.TextDark
import kotlinx.coroutines.delay

// Data structure for tips (Mock data)
data class ScalpTip(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val delayMs: Long // For staggered animation
)

private val mockTips = listOf(
    ScalpTip("Hydration is Key", "Drink at least 8 glasses of water daily to maintain skin and scalp moisture.", Icons.Default.Spa, 200),
    ScalpTip("Monitor Sugar Intake", "High glycemic diets can increase oil production. Opt for complex carbs.", Icons.Default.MonitorWeight, 400),
    ScalpTip("Gentle Washing", "Avoid hot water and harsh sulfates; they strip natural oils, leading to irritation.", Icons.Default.LocalHospital, 600)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scalp Health Tips") },
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
        LazyColumn(
            modifier = Modifier
                .background(PureCream) // 🎨 Changed from SoftCream
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Discover the best practices for a healthier scalp.",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextDark,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            // Scrollable List of Animated Tips (Animation requirement)
            itemsIndexed(mockTips) { index, tip ->
                AnimatedTipCard(tip = tip)
            }
        }
    }
}

@Composable
fun AnimatedTipCard(tip: ScalpTip) {
    var visible by remember { mutableStateOf(false) }

    // Staggered Animation Effect (Adheres to Animation Requirement)
    LaunchedEffect(key1 = tip.title) {
        delay(tip.delayMs)
        visible = true
    }

    // AnimatedVisibility for the simple fade-in effect
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Icon on the left (clean design)
                Icon(
                    imageVector = tip.icon,
                    contentDescription = tip.title,
                    tint = AccentTerra, // 🎨 Changed from LeafGreen
                    modifier = Modifier.size(32.dp)
                )
                Spacer(Modifier.width(16.dp))

                // Title and Subtitle in the center
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        tip.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextDark,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        tip.subtitle,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Navigation Arrow
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Details",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}