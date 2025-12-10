package com.example.nutriscalp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutriscalp.R
import com.example.nutriscalp.ui.theme.AccentPrimary
import com.example.nutriscalp.ui.theme.AccentSecondary
import com.example.nutriscalp.AppViewModel
import kotlinx.coroutines.delay

// Data structure for tips (Mock data)
data class ScalpTip(
    val title: String,
    val subtitle: String,
    val imageResId: Int,
    val delayMs: Long,
    val conditionTag: String,
    val icon: ImageVector? = null, // NEW: Icon for condition
    val color: Long = 0xFF6200EE // NEW: Color for condition tag
)

// Comprehensive list of tips categorized by scalp condition
private val allTips = listOf(
    // Tips for Oily Scalp
    ScalpTip(
        "Reduce Washing Frequency",
        "Over-washing can strip oils, leading to rebound overproduction. Aim for washing every 2-3 days instead of daily.",
        R.drawable.tip_gentle_wash,
        0,
        "Oiliness",
        Icons.Default.WaterDrop,
        0xFF4CAF50
    ),
    ScalpTip(
        "Monitor Sugar Intake",
        "High glycemic diets can increase sebum production. Opt for complex carbs like whole grains, oats, and vegetables.",
        R.drawable.tipp_carb_control,
        0,
        "Oiliness",
        Icons.Default.LocalFireDepartment,
        0xFF4CAF50
    ),
    ScalpTip(
        "Use Clay Masks Weekly",
        "A detoxifying clay mask once a week can absorb excess sebum without stripping the scalp's natural moisture barrier.",
        R.drawable.tip_gentle_wash,
        0,
        "Oiliness",
        Icons.Default.WaterDrop,
        0xFF4CAF50
    ),

    // Tips for Dry Scalp
    ScalpTip(
        "Hydration is Key",
        "Drink at least 8 glasses of water daily to maintain skin and scalp moisture. Dehydration directly affects scalp health.",
        R.drawable.tip_hydration,
        0,
        "Dryness",
        Icons.Default.WaterDrop,
        0xFF2196F3
    ),
    ScalpTip(
        "Avoid Harsh Shampoos",
        "Use sulfate-free and moisturizing products to retain natural oils. Look for ingredients like shea butter and coconut oil.",
        R.drawable.tip_gentle_wash,
        0,
        "Dryness",
        Icons.Default.Warning,
        0xFF2196F3
    ),
    ScalpTip(
        "Use Humidifiers",
        "Increase the moisture in your environment, especially during dry seasons. Aim for 40-60% humidity in your living space.",
        R.drawable.tip_hydration,
        0,
        "Dryness",
        Icons.Default.WaterDrop,
        0xFF2196F3
    ),

    // Tips for Inflammation
    ScalpTip(
        "Eat Anti-inflammatory Foods",
        "Increase Omega-3s from salmon, walnuts, and flaxseeds. These help reduce irritation, redness, and scalp inflammation.",
        R.drawable.tipp_carb_control,
        0,
        "Inflammation",
        Icons.Default.LocalFireDepartment,
        0xFFF44336
    ),
    ScalpTip(
        "Avoid Scratching",
        "Minimize physical irritation; use cooling aloe vera gel instead of scratching. Keep nails short and clean.",
        R.drawable.tip_gentle_wash,
        0,
        "Inflammation",
        Icons.Default.Warning,
        0xFFF44336
    ),
    ScalpTip(
        "Lukewarm Water Only",
        "Hot water can aggravate inflamed skin. Use cool or lukewarm water when washing to soothe irritation.",
        R.drawable.tip_gentle_wash,
        0,
        "Inflammation",
        Icons.Default.WaterDrop,
        0xFFF44336
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsScreen(appViewModel: AppViewModel, onBack: () -> Unit) {
    val scalpScore by appViewModel.scalpScore.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }

    // Filtering Logic based on ScalpScore state and selected filter
    val filteredTips = remember(scalpScore, selectedFilter) {
        val tips = mutableListOf<ScalpTip>()

        if (selectedFilter == "All") {
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
        } else {
            // Filter by selected category
            tips.addAll(allTips.filter { it.conditionTag == selectedFilter })
        }

        // Remove duplicates and re-index delayMs for staggered animation
        tips.distinctBy { it.title }.mapIndexed { index, tip ->
            tip.copy(delayMs = (index + 1) * 200L)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Scalp Health Tips",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AccentPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.height(80.dp)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .fillMaxSize()
        ) {
            // Header with user's current scalp status
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AccentSecondary.copy(alpha = 0.1f)),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "Your Scalp Profile",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "Dryness: ${scalpScore.dryness} • Oiliness: ${scalpScore.oiliness} • Inflammation: ${scalpScore.inflammation}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Info",
                        tint = AccentPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Filter Chips
            Text(
                "Filter by Condition:",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Oiliness", "Dryness", "Inflammation").forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        modifier = Modifier,
                        enabled = true
                    )
                }
            }

            // Tips Count
            Text(
                "${filteredTips.size} tips found",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            // Tips List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                itemsIndexed(filteredTips) { index, tip ->
                    AnimatedTipCard(tip = tip, index = index)
                }
            }
        }
    }
}

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    androidx.compose.material3.FilterChip(
        selected = selected,
        onClick = onClick,
        label = label,
        modifier = modifier,
        enabled = enabled
    )
}

@Composable
fun AnimatedTipCard(tip: ScalpTip, index: Int) {
    var visible by remember { mutableStateOf(false) }

    // Staggered Animation Effect
    LaunchedEffect(key1 = tip.title) {
        delay(tip.delayMs)
        visible = true
    }

    // AnimatedVisibility with scale and fade
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 600)) +
                scaleIn(initialScale = 0.9f, animationSpec = tween(500))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp), // BIGGER CARD
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // BIG IMAGE on left
                Box(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = tip.imageResId),
                        contentDescription = tip.title,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(Modifier.width(16.dp))

                // Content on right
                Column(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Condition Tag
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        tip.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = tip.conditionTag,
                                tint = Color(tip.color),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            tip.conditionTag,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(tip.color),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Title and Subtitle
                    Column {
                        Text(
                            tip.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            lineHeight = 20.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            tip.subtitle,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            fontSize = 13.sp,
                            maxLines = 3,
                            lineHeight = 16.sp
                        )
                    }

                    // Action Button
                    Button(
                        onClick = { /* Action for more details */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentPrimary.copy(alpha = 0.1f),
                            contentColor = AccentPrimary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Text(
                            "Learn More",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}