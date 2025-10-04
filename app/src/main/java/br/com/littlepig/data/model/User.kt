package br.com.littlepig.data.model

import java.util.Date

data class User(
    val id: String,
    val name: String,
    val email: String,
    val balance: Long,
    val createdAt: Date,
    val updatedAt: Date
)
