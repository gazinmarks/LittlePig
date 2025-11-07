package br.com.littlepig.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.dateFormat(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    return formatter.format(Date(this))
}