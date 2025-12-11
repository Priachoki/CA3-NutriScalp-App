package com.example.nutriscalp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutriscalp.ui.theme.AccentPrimary
import com.example.nutriscalp.ui.theme.AccentSecondary
import com.example.nutriscalp.AppViewModel
import kotlinx.coroutines.delay
import coil.compose.AsyncImage


// Data structure for tips with detailed content
data class ScalpTip(
    val title: String,
    val subtitle: String,
    val detailedContent: String, // NEW: Full detailed explanation
    val imageUrl: String,
    val delayMs: Long,
    val conditionTag: String,
    val icon: ImageVector? = null,
    val color: Long = 0xFF6200EE
)

// Comprehensive list of tips with detailed content
private val allTips = listOf(
    // ---------------- OILINESS ----------------
    ScalpTip(
        "Reduce Washing Frequency",
        "Over-washing forces scalp to produce even more oil. Wash every 2–3 days.",
        detailedContent = "When you wash too frequently, the scalp produces excess oil to compensate. This creates a cycle of rebound oiliness.\n\nRecommended routine:\n• Wash every 2–3 days\n• Use gentle sulfate-free shampoos\n• Avoid scrubbing aggressively\n• Rinse thoroughly\n\nYour scalp will regulate oil production in 2–4 weeks.",
        imageUrl = "https://i.pinimg.com/736x/f9/f3/bf/f9f3bff47eeef955e271c7953dad03d0.jpg",
        delayMs = 0,
        conditionTag = "Oiliness",
        icon = Icons.Default.WaterDrop,
        color = 0xFF4CAF50
    ),
    ScalpTip(
        "Monitor Sugar Intake",
        "High sugar increases sebum production. Choose complex carbs instead.",
        detailedContent = "High sugar intake spikes insulin, which increases androgen hormones responsible for stimulating oil glands.\n\nFoods to avoid:\n• Sugary drinks\n• Pastries and cakes\n• White bread, pasta\n\nBetter alternatives:\n• Oats, brown rice, quinoa\n• Veggies and legumes\n• Nuts and seeds\n\nBalancing blood sugar helps normalize oil production.",
        imageUrl = "https://i.pinimg.com/1200x/5f/f1/75/5ff1751a76d23961c3f7be978b613ea2.jpg",
        delayMs = 0,
        conditionTag = "Oiliness",
        icon = Icons.Default.LocalFireDepartment,
        color = 0xFF4CAF50
    ),
    ScalpTip(
        "Use Clay Masks Weekly",
        "Clay removes excess oil without irritating the scalp.",
        detailedContent = "Clay masks help absorb excess sebum and detoxify pores.\n\nBest clay types:\n• Bentonite (strong oil absorption)\n• Kaolin (gentle, for sensitive scalps)\n• Rhassoul (rich in minerals)\n\nHow to apply:\n• Apply on dry scalp for 10–15 mins\n• Do not let clay dry fully\n• Rinse with lukewarm water\n\nUse weekly for best results.",
        imageUrl = "https://i.pinimg.com/736x/d5/b5/44/d5b544d0fcc7d2ff62051ac299eb7959.jpg",
        delayMs = 0,
        conditionTag = "Oiliness",
        icon = Icons.Default.WaterDrop,
        color = 0xFF4CAF50
    ),

    // ---------------- DRYNESS ----------------
    ScalpTip(
        "Hydration is Key",
        "Drink enough water to support scalp moisture.",
        detailedContent = "Your scalp requires internal hydration to maintain elasticity and prevent flaking.\n\nHydration tips:\n• Drink 8–10 cups of water daily\n• Add water-rich foods (melon, cucumber)\n• Reduce caffeine and alcohol\n\nProper hydration improves scalp moisture within 2–3 weeks.",
        imageUrl = "https://i.pinimg.com/736x/f8/fa/7d/f8fa7df8a031e02ee3fa5d8a3de6fe78.jpg",
        delayMs = 0,
        conditionTag = "Dryness",
        icon = Icons.Default.WaterDrop,
        color = 0xFF2196F3
    ),
    ScalpTip(
        "Avoid Harsh Shampoos",
        "Sulfates strip natural moisture, causing dryness and irritation.",
        detailedContent = "Sulfates strip natural oils, leading to dryness and irritation.\n\nAvoid:\n• Sodium Lauryl Sulfate (SLS)\n• Sodium Laureth Sulfate (SLES)\n• Strong fragrances\n\nLook for shampoos containing:\n• Shea butter\n• Coconut oil\n• Glycerin\n\nSwitching to mild cleansers restores the scalp barrier.",
        imageUrl = "https://i.pinimg.com/1200x/35/af/85/35af85d2a73f43f8fd7df9292c31f8e5.jpg",
        delayMs = 0,
        conditionTag = "Dryness",
        icon = Icons.Default.Warning,
        color = 0xFF2196F3
    ),
    ScalpTip(
        "Use Humidifiers",
        "Add moisture to air to prevent scalp dehydration.",
        detailedContent = "Low humidity environments pull moisture from the scalp.\n\nBenefits:\n• Reduces dryness and flaking\n• Prevents tightness and itching\n• Improves skin hydration overnight\n\nIdeal humidity level: 40–60%.\nUse especially during winter.",
        imageUrl = "https://i.pinimg.com/736x/cb/85/d7/cb85d78d1acb5577c6dfb44dced71ca3.jpg",
        delayMs = 0,
        conditionTag = "Dryness",
        icon = Icons.Default.WaterDrop,
        color = 0xFF2196F3
    ),

    ScalpTip(
        "Eat Anti-inflammatory Foods",
        "Omega-3 foods reduce redness and inflammation.",
        detailedContent = "Inflammation can damage hair follicles and disrupt scalp health.\n\nBest anti-inflammatory foods:\n• Salmon, walnuts, chia seeds\n• Ginger and turmeric\n• Spinach, kale\n• Green tea\n\nAvoid:\n• Processed foods\n• Sugary snacks\n• Fried foods\n\nResults appear in 4–6 weeks of consistency.",
        imageUrl = "https://i.pinimg.com/736x/36/8f/75/368f758b5497126de43a16aff78f56dc.jpg",
        delayMs = 0,
        conditionTag = "Inflammation",
        icon = Icons.Default.LocalFireDepartment,
        color = 0xFFF44336
    ),
    ScalpTip(
        "Avoid Scratching",
        "Scratching introduces bacteria and worsens inflammation.",
        detailedContent = "Scratching worsens inflammation, introduces bacteria, and slows healing.\n\nInstead of scratching:\n• Use aloe vera gel\n• Apply cold compress\n• Massage gently with fingertips\n\nKeep nails short and clean to avoid scalp injury.",
        imageUrl = "https://i.pinimg.com/1200x/27/cc/b9/27ccb9853ed4ccced03567b73f7902c5.jpg",
        delayMs = 0,
        conditionTag = "Inflammation",
        icon = Icons.Default.Warning,
        color = 0xFFF44336
    ),
    ScalpTip(
        "Lukewarm Water Only",
        "Hot water increases redness and irritation.",
        detailedContent = "Hot water increases redness, irritation, and disrupts scalp barrier.\n\nRecommendations:\n• Use lukewarm water (not steaming hot)\n• Finish with a cool rinse\n• Limit showers to 10 minutes\n\nThis reduces flare-ups almost immediately.",
        imageUrl = "https://i.pinimg.com/736x/12/be/0d/12be0de063fb400d2ee74e99c41add3f.jpg",
        delayMs = 0,
        conditionTag = "Inflammation",
        icon = Icons.Default.WaterDrop,
        color = 0xFFF44336
    )
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsScreen(appViewModel: AppViewModel, onBack: () -> Unit) {
    val scalpScore by appViewModel.scalpScore.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }

    // Filtering Logic based on ScalpScore state and selected filter
    val filteredTips = remember(scalpScore, selectedFilter) {
        when (selectedFilter) {

            "All" -> {
                // Show ALL 9 tips always
                allTips.mapIndexed { index, tip ->
                    tip.copy(delayMs = (index + 1) * 200L)
                }
            }

            else -> {
                // Filter by selected condition (Oiliness, Dryness, Inflammation)
                allTips
                    .filter { it.conditionTag == selectedFilter }
                    .mapIndexed { index, tip ->
                        tip.copy(delayMs = (index + 1) * 200L)
                    }
            }
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
    var expanded by remember { mutableStateOf(false) } // NEW: Track expanded state

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
                .animateContentSize(), // NEW: Animates size changes smoothly
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Image on left
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = tip.imageUrl,
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
                        modifier = Modifier.weight(1f)
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

                        Spacer(Modifier.height(8.dp))

                        // Title
                        Text(
                            tip.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            lineHeight = 20.sp
                        )

                        Spacer(Modifier.height(8.dp))

                        // Subtitle (always visible)
                        Text(
                            tip.subtitle,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            fontSize = 13.sp,
                            maxLines = if (expanded) Int.MAX_VALUE else 3,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Expanded detailed content
                if (expanded) {
                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(16.dp))

                    Text(
                        tip.detailedContent,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    )

                    Spacer(Modifier.height(12.dp))
                }

                // Learn More / Collapse Button
                TextButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = AccentPrimary
                    )
                ) {
                    Text(
                        if (expanded) "Show Less" else "Learn More",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}