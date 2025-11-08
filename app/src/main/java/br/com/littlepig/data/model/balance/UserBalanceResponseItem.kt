package br.com.littlepig.data.model.balance

import java.math.BigDecimal

data class UserBalanceResponseItem(
    val tag: String,
    val saldo: BigDecimal
)
