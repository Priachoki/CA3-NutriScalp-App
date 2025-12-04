package com.example.nutriscalp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutriscalp.AppViewModel
import com.example.nutriscalp.ui.theme.LeafGreen
import com.example.nutriscalp.ui.theme.SoftCream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(appViewModel: AppViewModel, onBack: () -> Unit) {
    // Use DataStore: Collect the preference from the ViewModel
    val isDarkModeEnabled by appViewModel.isDarkMode.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings (DataStore Demo)") },
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
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Text(
                        text = "Enable Dark Mode (Persistent Setting)",
                        fontSize = 16.sp
                    )
                    // Use DataStore: Toggle the preference
                    Switch(
                        checked = isDarkModeEnabled,
                        onCheckedChange = { appViewModel.toggleDarkMode(it) },
                        colors = SwitchDefaults.colors(checkedTrackColor = LeafGreen)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "This setting is saved using Android DataStore and will persist even if you close and reopen the app.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}