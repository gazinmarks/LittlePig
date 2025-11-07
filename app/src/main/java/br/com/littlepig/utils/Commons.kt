package br.com.littlepig.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Commons {
    val KEY_USER_TOKEN = stringPreferencesKey("USER_TOKEN")
    const val LOG_TAG = "LittlePigApp"
}