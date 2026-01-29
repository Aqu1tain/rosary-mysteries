package com.rosary.mysteries.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesManager(private val context: Context) {

    private val languageKey = stringPreferencesKey("language")

    val language: Flow<String?> = context.dataStore.data.map { it[languageKey] }

    suspend fun setLanguage(language: String) {
        context.dataStore.edit { it[languageKey] = language }
    }
}
