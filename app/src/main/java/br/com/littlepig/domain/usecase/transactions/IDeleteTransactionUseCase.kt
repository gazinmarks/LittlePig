package br.com.littlepig.domain.usecase.transactions

import br.com.littlepig.data.model.balance.DeleteResponse

interface IDeleteTransactionUseCase {
    suspend operator fun invoke(id: String): Result<DeleteResponse>
}