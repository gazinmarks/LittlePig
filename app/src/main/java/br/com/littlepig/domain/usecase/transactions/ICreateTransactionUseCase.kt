package br.com.littlepig.domain.usecase.transactions

import br.com.littlepig.data.model.balance.Balance
import br.com.littlepig.common.Result
import br.com.littlepig.domain.enums.DomainError
import java.math.BigDecimal

interface ICreateTransactionUseCase {
    suspend operator fun invoke(
        description: String,
        value: BigDecimal,
        type: String,
        date: Long
    ): Result<Balance, DomainError>
}