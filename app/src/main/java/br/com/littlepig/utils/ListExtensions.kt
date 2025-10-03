package br.com.littlepig.utils

fun List<String>.isFieldsInvalid(): Boolean = this.any { it.isEmpty() }