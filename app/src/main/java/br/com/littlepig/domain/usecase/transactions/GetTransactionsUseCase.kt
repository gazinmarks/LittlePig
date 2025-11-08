package br.com.littlepig.domain.usecase.transactions

import android.util.Log
import br.com.littlepig.data.model.balance.Balance
import br.com.littlepig.data.repository.IUserRepository
import br.com.littlepig.domain.exceptions.AppExceptions
import br.com.littlepig.preferences.IDataStorePreferences
import br.com.littlepig.utils.Commons.KEY_USER_TOKEN
import br.com.littlepig.utils.dateFormat
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: IUserRepository,
    private val dataStore: IDataStorePreferences
) : IGetTransactionsUseCase {
    override suspend fun invoke(date: Long): Result<List<Balance>> = runCatching {
        val formattedDate = date.dateFormat()

        val token =
            dataStore.read(KEY_USER_TOKEN) ?: throw AppExceptions.TokenNotFound("Token not found")

        val response = repository.getAllTransactions(formattedDate, "Bearer $token")

        when {
            response.isSuccessful -> {
                response.body() ?: throw AppExceptions.EmptyResponseException("Empty response")
            }

            response.code() == 401 -> {
                throw AppExceptions.UnauthorizedException("Request unauthorized")
            }

            else -> {
                throw AppExceptions.UnknownException("Erro ${response.code()} - ${response.body()}")
            }
        }
    }.onSuccess {
        Log.d("Log", "Sucesso do transactions usecase $it")
    }.onFailure {
        Log.d("Log", "Erro do transactions usecase $it")
    }
}
