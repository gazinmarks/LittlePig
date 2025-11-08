package br.com.littlepig.data.model.balance

import java.math.BigDecimal
import java.util.Date

data class Balance(
    val id: String,
    val description: String,
    val value: BigDecimal,
    val type: String,
    val date: Date,
    val createdAt: String,
    val updatedAt: String
)
