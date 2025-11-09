package br.com.littlepig.domain.usecase.transactions

import android.util.Log
import br.com.littlepig.data.model.balance.Balance
import br.com.littlepig.data.model.balance.TransactionRequest
import br.com.littlepig.data.repository.IUserRepository
import br.com.littlepig.domain.exceptions.AppExceptions
import br.com.littlepig.preferences.IDataStorePreferences
import br.com.littlepig.utils.Commons.KEY_USER_TOKEN
import br.com.littlepig.utils.dateFormat
import java.math.BigDecimal
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val repository: IUserRepository,
    private val dataStore: IDataStorePreferences
) : ICreateTransactionUseCase {
    override suspend fun invoke(
        description: String,
        value: BigDecimal,
        type: String,
        date: Long
    ): Result<Balance> = runCatching {
        val formattedDate = date.dateFormat()

        val token =
            dataStore.read(KEY_USER_TOKEN) ?: throw AppExceptions.TokenNotFound("Token not found")

        val transactionRequest = TransactionRequest(description, value, type, formattedDate)

        val response = repository.createTransaction(
            transactionRequest, "Bearer $token"
        )

        when {
            response.isSuccessful -> {
                response.body() ?: throw AppExceptions.Error("")
            }

            response.code() == 401 -> {
                throw AppExceptions.UnauthorizedException("Request unauthorized")
            }

            else -> {
                throw AppExceptions.UnknownException("Erro ${response.code()} - ${response.body()}")
            }
        }
    }.onSuccess {
        Log.d("log", "transacao criada com sucesso")
    }.onFailure {
        Log.d("log", "falha na criacao da transacao")
    }
}
