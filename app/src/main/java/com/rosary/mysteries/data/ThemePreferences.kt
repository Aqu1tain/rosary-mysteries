package com.rosary.mysteries.data

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

enum class ThemeMode { SYSTEM, LIGHT, DARK }

private val Context.dataStore by preferencesDataStore(name = "settings")
private val THEME_KEY = stringPreferencesKey("theme_mode")
private val LUMINOUS_ENABLED_KEY = booleanPreferencesKey("luminous_enabled")

class AppPreferences(private val context: Context) {

    val themeMode: Flow<ThemeMode> = context.dataStore.data.map { prefs ->
        ThemeMode.entries.find { it.name.lowercase() == prefs[THEME_KEY] } ?: ThemeMode.SYSTEM
    }

    val luminousEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[LUMINOUS_ENABLED_KEY] ?: true
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { it[THEME_KEY] = mode.name.lowercase() }
    }

    suspend fun setLuminousEnabled(enabled: Boolean) {
        context.dataStore.edit { it[LUMINOUS_ENABLED_KEY] = enabled }
    }

    fun getLuminousEnabledBlocking(): Boolean = runBlocking {
        luminousEnabled.first()
    }
}

@Composable
fun rememberThemePreferences(): AppPreferences {
    val context = LocalContext.current
    return remember { AppPreferences(context) }
}

@Composable
fun rememberAppPreferences(): AppPreferences {
    val context = LocalContext.current
    return remember { AppPreferences(context) }
}
