package br.com.littlepig.domain.usecase.transactions

import br.com.littlepig.common.Result
import br.com.littlepig.data.model.balance.DeleteResponse
import br.com.littlepig.domain.enums.DomainError

interface IDeleteTransactionUseCase {
    suspend operator fun invoke(id: String): Result<DeleteResponse, DomainError>
}