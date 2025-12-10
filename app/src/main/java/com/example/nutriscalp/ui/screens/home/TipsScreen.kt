package com.example.nutriscalp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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

// Data structure for tips with detailed content
data class ScalpTip(
    val title: String,
    val subtitle: String,
    val detailedContent: String, // NEW: Full detailed explanation
    val imageResId: Int,
    val delayMs: Long,
    val conditionTag: String,
    val icon: ImageVector? = null,
    val color: Long = 0xFF6200EE
)

// Comprehensive list of tips with detailed content
private val allTips = listOf(
    // Tips for Oily Scalp
    ScalpTip(
        "Reduce Washing Frequency",
        "Over-washing can strip oils, leading to rebound overproduction. Aim for washing every 2-3 days instead of daily.",
        "When you wash your hair too frequently, you strip away the natural oils (sebum) that protect your scalp. Your scalp responds by producing even more oil to compensate, creating a cycle of overproduction.\n\nKey strategies:\n• Gradually extend time between washes (start with every other day)\n• Use dry shampoo on non-wash days\n• Focus shampoo on scalp only, not hair length\n• Rinse thoroughly to prevent buildup\n\nWith consistent practice, your scalp will regulate oil production naturally within 2-4 weeks.",
        R.drawable.tip_gentle_wash,
        0,
        "Oiliness",
        Icons.Default.WaterDrop,
        0xFF4CAF50
    ),
    ScalpTip(
        "Monitor Sugar Intake",
        "High glycemic diets can increase sebum production. Opt for complex carbs like whole grains, oats, and vegetables.",
        "High-glycemic foods cause rapid spikes in blood sugar and insulin, which triggers increased androgen hormones. These hormones stimulate sebaceous glands to produce more oil.\n\nFoods to limit:\n• White bread, pastries, sugary drinks\n• Processed snacks and candy\n• White rice and pasta\n\nBetter alternatives:\n• Whole grain bread and brown rice\n• Steel-cut oats and quinoa\n• Vegetables, legumes, and nuts\n• Low-glycemic fruits like berries\n\nMaintaining stable blood sugar helps regulate oil production from the inside out.",
        R.drawable.tipp_carb_control,
        0,
        "Oiliness",
        Icons.Default.LocalFireDepartment,
        0xFF4CAF50
    ),
    ScalpTip(
        "Use Clay Masks Weekly",
        "A detoxifying clay mask once a week can absorb excess sebum without stripping the scalp's natural moisture barrier.",
        "Clay masks work through a process called adsorption, where the negatively charged clay particles attract and bind to positively charged impurities and excess oils.\n\nBest clay types:\n• Bentonite clay - most absorbent\n• Kaolin clay - gentler for sensitive scalps\n• Rhassoul clay - mineral-rich option\n\nApplication tips:\n• Apply to dry scalp before washing\n• Leave on for 10-15 minutes (not until completely dry)\n• Rinse thoroughly with lukewarm water\n• Follow with a light conditioner on hair ends only\n\nUse once weekly to maintain balance without over-drying.",
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
        "Your scalp is skin, and like all skin, it needs adequate hydration from within. Dehydration reduces the moisture content in all your body's tissues, including your scalp.\n\nHydration benefits:\n• Maintains scalp's natural moisture barrier\n• Supports healthy cell turnover\n• Improves nutrient delivery to hair follicles\n• Reduces flaking and itchiness\n\nDaily water intake guide:\n• 8-10 glasses (64-80 oz) as baseline\n• More if exercising or in hot weather\n• Include water-rich foods (cucumbers, watermelon, oranges)\n• Limit dehydrating beverages (coffee, alcohol)\n\nConsistent hydration shows results in 2-3 weeks.",
        R.drawable.tip_hydration,
        0,
        "Dryness",
        Icons.Default.WaterDrop,
        0xFF2196F3
    ),
    ScalpTip(
        "Avoid Harsh Shampoos",
        "Use sulfate-free and moisturizing products to retain natural oils. Look for ingredients like shea butter and coconut oil.",
        "Sulfates (SLS, SLES) are aggressive detergents that strip away your scalp's protective oils, leading to dryness, irritation, and flaking.\n\nIngredients to avoid:\n• Sodium Lauryl Sulfate (SLS)\n• Sodium Laureth Sulfate (SLES)\n• Alcohol (especially denatured alcohol)\n• Strong fragrances\n\nLook for instead:\n• Shea butter - deep moisturizer\n• Coconut oil - penetrates hair shaft\n• Argan oil - rich in vitamin E\n• Hyaluronic acid - retains moisture\n• Glycerin - natural humectant\n\nGentle cleansers:\n• Coco-glucoside\n• Decyl glucoside\n• Cocamidopropyl betaine\n\nYour scalp will feel less tight and itchy within 1-2 weeks of switching.",
        R.drawable.tip_gentle_wash,
        0,
        "Dryness",
        Icons.Default.Warning,
        0xFF2196F3
    ),
    ScalpTip(
        "Use Humidifiers",
        "Increase the moisture in your environment, especially during dry seasons. Aim for 40-60% humidity in your living space.",
        "Low humidity, especially during winter heating or summer AC, pulls moisture from your skin and scalp into the air, causing dehydration and flaking.\n\nHumidifier benefits:\n• Prevents moisture evaporation from scalp\n• Reduces static and breakage\n• Improves overall skin hydration\n• Better sleep quality\n\nBest practices:\n• Place humidifier in bedroom while sleeping\n• Clean weekly to prevent mold/bacteria\n• Use distilled water to avoid mineral buildup\n• Maintain 40-60% humidity (use hygrometer)\n\nAdditional environmental tips:\n• Lower thermostat slightly in winter\n• Keep bedroom door closed to maintain humidity\n• Add houseplants (natural humidifiers)\n• Take shorter, cooler showers\n\nYou'll notice softer scalp within days of consistent use.",
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
        "Chronic inflammation can damage hair follicles and disrupt the scalp's natural balance. Omega-3 fatty acids are powerful anti-inflammatory compounds that work systemically.\n\nBest Omega-3 sources:\n• Fatty fish (salmon, mackerel, sardines) - 2-3x weekly\n• Walnuts - handful daily\n• Flaxseeds - 1-2 tbsp ground daily\n• Chia seeds - 1 tbsp daily\n• Hemp seeds - rich in both omega-3 and omega-6\n\nOther anti-inflammatory foods:\n• Turmeric (with black pepper for absorption)\n• Ginger - fresh or as tea\n• Leafy greens (spinach, kale)\n• Berries (blueberries, strawberries)\n• Green tea - 2-3 cups daily\n\nFoods to limit:\n• Refined sugars and processed foods\n• Trans fats and fried foods\n• Excessive alcohol\n• High-sodium foods\n\nConsistent dietary changes show improvement in 4-6 weeks.",
        R.drawable.tipp_carb_control,
        0,
        "Inflammation",
        Icons.Default.LocalFireDepartment,
        0xFFF44336
    ),
    ScalpTip(
        "Avoid Scratching",
        "Minimize physical irritation; use cooling aloe vera gel instead of scratching. Keep nails short and clean.",
        "Scratching creates a vicious cycle: it damages the scalp barrier, introduces bacteria, and triggers more inflammation and itching.\n\nWhy scratching is harmful:\n• Breaks skin barrier, allowing infection\n• Damages hair follicles\n• Spreads bacteria under fingernails\n• Creates micro-wounds that take time to heal\n• Can lead to scarring in severe cases\n\nAlternatives to scratching:\n• Aloe vera gel (refrigerate for cooling effect)\n• Scalp massage with fingertips (not nails)\n• Cold compress for immediate relief\n• Tea tree oil (diluted) for antibacterial effect\n• Witch hazel for soothing irritation\n\nPrevention strategies:\n• Keep nails trimmed short\n• Wear cotton gloves at night if needed\n• Identify and address root cause (dandruff, dryness, etc.)\n• Use medicated shampoos if prescribed\n\nIf scratching persists, consult a dermatologist as it may indicate an underlying condition.",
        R.drawable.tip_gentle_wash,
        0,
        "Inflammation",
        Icons.Default.Warning,
        0xFFF44336
    ),
    ScalpTip(
        "Lukewarm Water Only",
        "Hot water can aggravate inflamed skin. Use cool or lukewarm water when washing to soothe irritation.",
        "Hot water strips protective oils, dilates blood vessels, and exacerbates inflammation. It can also trigger histamine release, increasing itchiness and redness.\n\nEffects of hot water:\n• Strips natural sebum protection\n• Increases blood flow to inflamed areas\n• Dries out skin by opening pores excessively\n• Can trigger or worsen conditions like seborrheic dermatitis\n• Weakens hair cuticle, causing breakage\n\nIdeal water temperature:\n• Lukewarm (85-95°F / 29-35°C)\n• Cool rinse at end to close cuticles\n• Never hot enough to cause discomfort\n\nWashing technique:\n• Start with lukewarm water\n• Gentle massage (don't scrub)\n• Finish with cool rinse for 30 seconds\n• Pat dry gently (don't rub vigorously)\n\nAdditional tips:\n• Limit shower time to 10 minutes\n• Use gentle, pH-balanced cleansers\n• Apply leave-in treatment while hair is damp\n\nYou should feel less redness and irritation immediately after implementing this change.",
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
            val drynessValue = scalpScore.dryness.trim('%').toIntOrNull() ?: 0
            val oilinessValue = scalpScore.oiliness.trim('%').toIntOrNull() ?: 0

            if (drynessValue > 50) {
                tips.addAll(allTips.filter { it.conditionTag == "Dryness" })
            }

            if (oilinessValue > 50) {
                tips.addAll(allTips.filter { it.conditionTag == "Oiliness" })
            }

            if (scalpScore.inflammation.equals("High", ignoreCase = true)) {
                tips.addAll(allTips.filter { it.conditionTag == "Inflammation" })
            }

            if (tips.isEmpty()) {
                tips.addAll(allTips.filter { it.conditionTag == "Dryness" || it.conditionTag == "Oiliness" }.distinctBy { it.title })
            }
        } else {
            tips.addAll(allTips.filter { it.conditionTag == selectedFilter })
        }

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