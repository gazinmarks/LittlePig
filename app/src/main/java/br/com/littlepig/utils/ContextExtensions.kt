package br.com.littlepig.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String): Unit =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
