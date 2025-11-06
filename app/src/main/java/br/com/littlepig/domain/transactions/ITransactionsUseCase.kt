package br.com.littlepig.domain.transactions

import br.com.littlepig.data.model.balance.UserBalanceResponseItem

interface ITransactionsUseCase {
    suspend operator fun invoke(date: Long): Result<List<UserBalanceResponseItem>>
}