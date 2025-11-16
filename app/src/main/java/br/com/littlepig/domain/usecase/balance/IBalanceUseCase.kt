package br.com.littlepig.domain.usecase.balance

import br.com.littlepig.common.Result
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import br.com.littlepig.domain.enums.DomainError

interface IBalanceUseCase {
    suspend operator fun invoke(date: Long): Result<List<UserBalanceResponseItem>, DomainError>
}
