package br.com.littlepig.preferences

import androidx.datastore.preferences.core.Preferences

interface IDataStorePreferences {
    suspend fun <T> write(key: Preferences.Key<T>, value: T)
    suspend fun <T> read(key: Preferences.Key<T>): T?
}
