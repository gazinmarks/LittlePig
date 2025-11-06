package br.com.littlepig.data.model.balance

import java.math.BigDecimal
import java.util.Date

data class BalanceRequest( // o que vamos enviar para a api salvar como um saldo ou despesa
    val description: String,
    val value: BigDecimal,
    val type: String,
    val date: Date
)