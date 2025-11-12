package br.com.littlepig.domain.usecase.transactions

import br.com.littlepig.common.Result
import br.com.littlepig.data.model.balance.Balance
import br.com.littlepig.domain.enums.DomainError

interface IGetTransactionsUseCase {
    suspend operator fun invoke(date: Long): Result<List<Balance>, DomainError>
}