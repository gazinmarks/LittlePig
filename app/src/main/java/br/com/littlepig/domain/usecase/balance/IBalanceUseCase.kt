package br.com.littlepig.domain.usecase.balance

import br.com.littlepig.data.model.balance.UserBalanceResponseItem

interface IBalanceUseCase {
    suspend operator fun invoke(date: Long): Result<List<UserBalanceResponseItem>>
}