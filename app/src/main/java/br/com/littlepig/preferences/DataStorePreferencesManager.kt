package br.com.littlepig.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import br.com.littlepig.preferences.DataStorePreferencesManager.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

class DataStorePreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) : IDataStorePreferences {
    override suspend fun <T> write(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun <T> read(key: Preferences.Key<T>): T? {
        val preferences = context.dataStore.data.first()
        return preferences[key]
    }

    companion object {
        const val PREFERENCES_NAME = "PREFERENCES_STORE"
    }
}
