package com.example.nutriscalp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

// Creates an instance of DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager(context: Context?) {
    // FIX: Explicitly implement the DataStore interface for the mock object in Preview
    private val dataStore: DataStore<Preferences> = context?.dataStore ?: object : DataStore<Preferences> {
        override val data: Flow<Preferences> = flowOf(emptyPreferences())
        override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences {
            return transform(emptyPreferences())
        }
    }

    // Key for storing the dark mode preference
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")

    // Flow to expose the dark mode setting (Use DataStore)
    val isDarkMode: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }

    // Function to update the dark mode setting (Use DataStore)
    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }
}