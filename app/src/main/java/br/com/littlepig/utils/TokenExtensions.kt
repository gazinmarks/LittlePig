package br.com.littlepig.utils

import br.com.littlepig.preferences.DataStorePreferencesManager
import br.com.littlepig.utils.Commons.KEY_USER_TOKEN

suspend fun DataStorePreferencesManager.getUserToken(): String? = this.read(KEY_USER_TOKEN)
