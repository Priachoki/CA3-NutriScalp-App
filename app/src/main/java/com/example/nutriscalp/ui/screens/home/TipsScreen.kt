package com.example.nutriscalp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutriscalp.ui.theme.LeafGreen
import com.example.nutriscalp.ui.theme.SoftCream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsScreen(onBack: () -> Unit) {
    // Animation: controls visibility and animation state
    var visible by remember { mutableStateOf(false) }

    // Trigger the animation on composition
    LaunchedEffect(Unit) {
        visible = true
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
                    containerColor = LeafGreen,
                    titleContentColor = SoftCream,
                    navigationIconContentColor = SoftCream
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .background(SoftCream)
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Animation: Slide-in and Fade-in effect for the Tip Card
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 600)
                ) + fadeIn(animationSpec = tween(durationMillis = 600)),
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "💡 Daily Tip 💡",
                            fontSize = 20.sp,
                            color = LeafGreen,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Divider()
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Remember to gently massage your scalp for 5 minutes daily to boost blood circulation and promote nutrient delivery to hair follicles.",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}