package br.com.littlepig.domain.usecase.transactions

import br.com.littlepig.common.Result
import br.com.littlepig.common.mapError
import br.com.littlepig.common.mapToDomainErrors
import br.com.littlepig.data.model.balance.Balance
import br.com.littlepig.data.repository.transaction.ITransactionRepository
import br.com.littlepig.domain.enums.DomainError
import br.com.littlepig.preferences.IDataStorePreferences
import br.com.littlepig.utils.Commons.KEY_USER_TOKEN
import br.com.littlepig.utils.dateFormat
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: ITransactionRepository,
    private val dataStore: IDataStorePreferences
) : IGetTransactionsUseCase {
    override suspend fun invoke(date: Long): Result<List<Balance>, DomainError> {
        val formattedDate = date.dateFormat()

        val token =
            dataStore.read(KEY_USER_TOKEN) ?: return Result.Error(DomainError.TOKEN_NOT_FOUND)

        if (formattedDate.isBlank()) {
            return Result.Error(DomainError.EMPTY_TRANSACTION_DATE)
        }

        val result = repository.getAllTransactions(formattedDate, "Bearer $token")

        return result.mapError { networkError ->
            mapToDomainErrors(networkError)
        }
    }
}
