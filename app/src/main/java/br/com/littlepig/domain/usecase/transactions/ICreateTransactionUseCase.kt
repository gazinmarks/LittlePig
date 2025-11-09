package br.com.littlepig.domain.usecase.transactions

import br.com.littlepig.data.model.balance.Balance
import java.math.BigDecimal

interface ICreateTransactionUseCase {
    suspend operator fun invoke(
        description: String,
        value: BigDecimal,
        type: String,
        date: Long
    ): Result<Balance>
}