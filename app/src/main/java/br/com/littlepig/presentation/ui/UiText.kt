package br.com.littlepig.presentation.ui

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicResource(val value: String) : UiText()
    class StringResource(
        @StringRes val id: Int,
        vararg val args: Any
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is StringResource -> context.getString(id, *args)
            is DynamicResource -> value
        }
    }
}
