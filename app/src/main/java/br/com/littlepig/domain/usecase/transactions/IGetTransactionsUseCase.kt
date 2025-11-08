package br.com.littlepig.domain.usecase.transactions

import br.com.littlepig.data.model.balance.Balance

interface IGetTransactionsUseCase {
    suspend operator fun invoke(date: Long): Result<List<Balance>>
}