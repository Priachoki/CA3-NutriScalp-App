package com.example.nutriscalp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image // 💡 REQUIRED for local images
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale // 💡 REQUIRED
import androidx.compose.ui.res.painterResource // 💡 REQUIRED
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nutriscalp.R // 💡 REQUIRED for local resource IDs
import com.example.nutriscalp.ui.theme.AccentPrimary // 💡 NEW COLOR
import com.example.nutriscalp.ui.theme.BackgroundLight // 💡 NEW COLOR
import com.example.nutriscalp.ui.theme.TextDark
import kotlinx.coroutines.delay

// Data structure for tips (Mock data)
data class ScalpTip(
    val title: String,
    val subtitle: String,
    val imageResId: Int, // 💡 CHANGED to Int for local resource ID
    val delayMs: Long // For staggered animation
)

private val mockTips = listOf(
    // 💡 Using placeholder resource IDs (must match files in res/drawable)
    ScalpTip("Hydration is Key", "Drink at least 8 glasses of water daily to maintain skin and scalp moisture.", R.drawable.tip_hydration, 200),
    ScalpTip("Monitor Sugar Intake", "High glycemic diets can increase oil production. Opt for complex carbs.", R.drawable.tipp_carb_control, 400),
    ScalpTip("Gentle Washing", "Avoid hot water and harsh sulfates; they strip natural oils, leading to irritation.", R.drawable.tip_gentle_wash, 600)
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
                    containerColor = AccentPrimary, // 💡 Changed from AccentTerra
                    titleContentColor = BackgroundLight, // 💡 Changed from PureCream
                    navigationIconContentColor = BackgroundLight // 💡 Changed from PureCream
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .background(BackgroundLight) // 💡 Changed from PureCream
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
                // 💡 UPDATED: Replaced Icon with Image for local drawable resource
                Image(
                    painter = painterResource(id = tip.imageResId), // Use local resource ID
                    contentDescription = tip.title,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp)), // Rounded corners for image
                    contentScale = ContentScale.Crop // Use content scale for better fit
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