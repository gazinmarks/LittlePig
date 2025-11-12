package br.com.littlepig.domain.usecase.transactions

import br.com.littlepig.common.Result
import br.com.littlepig.common.mapError
import br.com.littlepig.common.mapToDomainErrors
import br.com.littlepig.data.enums.NetworkError
import br.com.littlepig.data.model.balance.Balance
import br.com.littlepig.data.model.balance.TransactionRequest
import br.com.littlepig.data.repository.transaction.ITransactionRepository
import br.com.littlepig.domain.enums.DomainError
import br.com.littlepig.preferences.IDataStorePreferences
import br.com.littlepig.utils.Commons.KEY_USER_TOKEN
import br.com.littlepig.utils.dateFormat
import java.math.BigDecimal
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val repository: ITransactionRepository,
    private val dataStore: IDataStorePreferences
) : ICreateTransactionUseCase {
    override suspend fun invoke(
        description: String, value: BigDecimal, type: String, date: Long
    ): Result<Balance, DomainError> {
        val formattedDate = date.dateFormat()

        val token =
            dataStore.read(KEY_USER_TOKEN) ?: return Result.Error(DomainError.TOKEN_NOT_FOUND)

        when {
            value <= BigDecimal.ZERO -> return Result.Error(DomainError.NO_TRANSACTION_VALUE)

            description.isBlank() -> return Result.Error(DomainError.EMPTY_TRANSACTION_DESCRIPTION)

            formattedDate.isBlank() -> return Result.Error(DomainError.EMPTY_TRANSACTION_DATE)
        }

        val transactionRequest = TransactionRequest(description, value, type, formattedDate)

        val result = repository.createTransaction(
            transactionRequest,
            "Bearer $token"
        )

        return result.mapError { networkError ->
            mapToDomainErrors(networkError)
        }
    }
}
