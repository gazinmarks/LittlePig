package br.com.littlepig.data.model.balance

import java.math.BigDecimal

data class TransactionRequest(
    val description: String,
    val value: BigDecimal,
    val type: String,
    val date: String
)
