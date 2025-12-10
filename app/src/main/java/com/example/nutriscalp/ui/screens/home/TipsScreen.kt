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
import com.example.nutriscalp.AppViewModel
import kotlinx.coroutines.delay

// Data structure for tips (Mock data)
data class ScalpTip(
    val title: String,
    val subtitle: String,  
    val imageResId: Int, // 💡 CHANGED to Int for local resource ID
    val delayMs: Long, // For staggered animation
    val conditionTag: String // 💡 NEW: Tag for filtering
)

// 💡 NEW: Comprehensive list of tips categorized by scalp condition
private val allTips = listOf(
    // Tips for Oily Scalp
    ScalpTip("Reduce Washing Frequency", "Over-washing can strip oils, leading to rebound overproduction. Aim for every 2-3 days.", R.drawable.tip_gentle_wash, 0, "Oiliness"),
    ScalpTip("Monitor Sugar Intake", "High glycemic diets can increase oil production. Opt for complex carbs.", R.drawable.tipp_carb_control, 0, "Oiliness"),
    ScalpTip("Use Clay Masks", "A detoxifying clay mask can absorb excess sebum without stripping the scalp.", R.drawable.tip_gentle_wash, 0, "Oiliness"),

    // Tips for Dry Scalp
    ScalpTip("Hydration is Key", "Drink at least 8 glasses of water daily to maintain skin and scalp moisture.", R.drawable.tip_hydration, 0, "Dryness"),
    ScalpTip("Avoid Harsh Shampoos", "Use sulfate-free and moisturizing products to retain natural oils.", R.drawable.tip_gentle_wash, 0, "Dryness"),
    ScalpTip("Use Humidifiers", "Increase the moisture in your environment, especially during dry seasons.", R.drawable.tip_hydration, 0, "Dryness"),

    // Tips for Inflammation
    ScalpTip("Eat Anti-inflammatory Foods", "Increase Omega-3s (salmon, walnuts) to reduce irritation and redness.", R.drawable.tipp_carb_control, 0, "Inflammation"),
    ScalpTip("Avoid Scratching", "Minimize physical irritation; use cooling products instead of scratching.", R.drawable.tip_gentle_wash, 0, "Inflammation"),
    ScalpTip("Lukewarm Water Only", "Hot water can aggravate inflamed skin. Use cool or lukewarm water when washing.", R.drawable.tip_gentle_wash, 0, "Inflammation")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsScreen(appViewModel: AppViewModel, onBack: () -> Unit) { // 💡 MODIFIED: Added AppViewModel

    // 💡 NEW: Collect the current Scalp Score
    val scalpScore by appViewModel.scalpScore.collectAsState()

    // 💡 NEW: Filtering Logic based on ScalpScore state
    val filteredTips = remember(scalpScore) {
        val tips = mutableListOf<ScalpTip>()

        // Parse the percentage strings to Int for threshold comparison
        val drynessValue = scalpScore.dryness.trim('%').toIntOrNull() ?: 0
        val oilinessValue = scalpScore.oiliness.trim('%').toIntOrNull() ?: 0

        // Filter tips based on conditions (assuming > 50% is 'high' or 'problematic')
        if (drynessValue > 50) {
            tips.addAll(allTips.filter { it.conditionTag == "Dryness" })
        }

        if (oilinessValue > 50) {
            tips.addAll(allTips.filter { it.conditionTag == "Oiliness" })
        }

        // Check for specific inflammation status
        if (scalpScore.inflammation.equals("High", ignoreCase = true)) {
            tips.addAll(allTips.filter { it.conditionTag == "Inflammation" })
        }

        // Fallback: If no condition is critical, show all tips for a general healthy routine
        if (tips.isEmpty()) {
            tips.addAll(allTips.filter { it.conditionTag == "Dryness" || it.conditionTag == "Oiliness" }.distinctBy { it.title })
        }

        // Remove duplicates and re-index delayMs for staggered animation
        tips.distinctBy { it.title }.mapIndexed { index, tip ->
            tip.copy(delayMs = (index + 1) * 200L)
        }
    }

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
                    containerColor = AccentPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background) // Use dynamic background color
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Tips for your current condition:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground, // Use dynamic text color
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            // 💡 MODIFIED: Use the filteredTips list
            itemsIndexed(filteredTips) { index, tip ->
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
                        color = MaterialTheme.colorScheme.onSurface, // Use dynamic text color
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        tip.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant // Use dynamic secondary text color
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