package com.example.nutriscalp.ui.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.nutriscalp.AppDestinations
import com.example.nutriscalp.AppViewModel
import com.example.nutriscalp.R
import com.example.nutriscalp.ScalpScore
import com.example.nutriscalp.ui.theme.AccentPrimary
import com.example.nutriscalp.ui.theme.AccentSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(appViewModel: AppViewModel, onNavigate: (String) -> Unit) {
    val scalpScore by appViewModel.scalpScore.collectAsState(initial = ScalpScore())
    val todayCalories by appViewModel.todayCalories.collectAsState(initial = 0)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.nutriscalp_logo),
                                contentDescription = "App Logo",
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(end = 12.dp)
                            )
                            Column {
                                Text(
                                    "Hello, Vanessa",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    "Welcome back!",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }


                        IconButton(
                            onClick = { onNavigate(AppDestinations.SETTINGS_ROUTE) },
                            modifier = Modifier
                                .size(52.dp)
                                .padding(end = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = AccentPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp)
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)
                .fillMaxSize()
        )
        {
            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        "Today's Health Summary",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentPrimary,
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    ScalpMetricsDisplay(scalpScore = scalpScore)

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Fastfood, contentDescription = "Calories", tint = AccentSecondary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Calories Logged Today:",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "$todayCalories kcal",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                            color = AccentPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "7-Day Calorie Trend",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    WeekCalorieChart()
                }
            }

//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 10.dp),
//                shape = RoundedCornerShape(24.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//            )
//            {
//                Column(modifier = Modifier.padding(24.dp)) {
//                    Text(
//                        "Weekly Nutrition Summary",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = AccentPrimary,
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Stats row
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        StatItem("Avg Daily", "1,850", "kcal")
//                        StatItem("Highest Day", "2,340", "kcal")
//                        StatItem("Total Week", "12,950", "kcal")
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Progress indicators
//                    Text(
//                        "Daily Goal Progress",
//                        style = MaterialTheme.typography.titleSmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    // Protein progress
//                    ProgressRow("Protein", 65, AccentPrimary)
//                    Spacer(modifier = Modifier.height(4.dp))
//
//                    // Carbs progress
//                    ProgressRow("Carbs", 85, AccentSecondary)
//                    Spacer(modifier = Modifier.height(4.dp))
//
//                    // Fat progress
//                    ProgressRow("Fat", 45, MaterialTheme.colorScheme.error)
//                }
//            }
        }
    }
}

@Composable
fun ScalpMetricsDisplay(scalpScore: ScalpScore) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        MetricItem("Dryness", scalpScore.dryness, MaterialTheme.colorScheme.error)
        MetricItem("Oiliness", scalpScore.oiliness, AccentSecondary)
        MetricItem("Inflammation", scalpScore.inflammation, AccentPrimary)
    }
}

@Composable
fun MetricItem(label: String, value: String, valueColor: Color, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(IntrinsicSize.Min)
    ) {
        Text(
            text = value,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = valueColor
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun WeekCalorieChart() {
    // Sample data for last 7 days
    val weekData = listOf(
        Pair("Mon", 1850),
        Pair("Tue", 2200),
        Pair("Wed", 1950),
        Pair("Thu", 2340),
        Pair("Fri", 2100),
        Pair("Sat", 1800),
        Pair("Sun", 1600)
    )

    val maxCalories = weekData.maxOf { it.second }
    val chartHeight = 180.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(chartHeight)
            .clip(RoundedCornerShape(8.dp))
            .background(AccentSecondary.copy(alpha = 0.05f))
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                modifier = Modifier
                    .width(30.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text("2.4k", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("1.8k", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("1.2k", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("0.6k", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("0", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Canvas(
                    modifier = Modifier.matchParentSize()
                ) {
                    val lineCount = 5
                    for (i in 0 until lineCount) {
                        val y = size.height * (i.toFloat() / (lineCount - 1))
                        drawLine(
                            color = Color.Gray.copy(alpha = 0.1f),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = 1f
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    weekData.forEach { (day, calories) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier.width(28.dp)
                        ) {
                            Text(
                                text = calories.toString(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentPrimary,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )

                            // Bar
                            val barHeight = (calories.toFloat() / maxCalories) * 120.dp
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(barHeight)
                                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                AccentPrimary.copy(alpha = 0.8f),
                                                AccentPrimary.copy(alpha = 0.3f)
                                            )
                                        )
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = AccentPrimary.copy(alpha = 0.9f),
                                        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                    )
                            )

                            // Day label
                            Text(
                                text = day,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String, unit: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = AccentPrimary
        )
        Text(
            text = unit,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProgressRow(label: String, percentage: Int, color: Color) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.bodyMedium,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = percentage / 100f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}