package br.com.littlepig.domain.usecase.transactions

import br.com.littlepig.common.Result
import br.com.littlepig.common.mapError
import br.com.littlepig.common.mapToDomainErrors
import br.com.littlepig.data.model.balance.DeleteResponse
import br.com.littlepig.data.repository.transaction.ITransactionRepository
import br.com.littlepig.domain.enums.DomainError
import br.com.littlepig.preferences.IDataStorePreferences
import br.com.littlepig.utils.Commons.KEY_USER_TOKEN
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: ITransactionRepository,
    private val dataStore: IDataStorePreferences
) : IDeleteTransactionUseCase {
    override suspend fun invoke(id: String): Result<DeleteResponse, DomainError> {
        val token =
            dataStore.read(KEY_USER_TOKEN) ?: return Result.Error(DomainError.TOKEN_NOT_FOUND)

        if (id.isBlank()) {
            return Result.Error(DomainError.INVALID_FORMAT)
        }

        val result = repository.deleteTransaction(id, "Bearer $token")

        return result.mapError { networkError ->
            mapToDomainErrors(networkError)
        }
    }
}
