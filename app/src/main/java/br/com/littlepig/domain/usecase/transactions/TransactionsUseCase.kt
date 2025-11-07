package br.com.littlepig.domain.usecase.transactions

import android.util.Log
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import br.com.littlepig.data.repository.IUserRepository
import br.com.littlepig.domain.exceptions.AppExceptions
import br.com.littlepig.preferences.DataStorePreferencesManager
import br.com.littlepig.utils.Commons.LOG_TAG
import br.com.littlepig.utils.dateFormat
import br.com.littlepig.utils.getUserToken
import javax.inject.Inject

class TransactionsUseCase @Inject constructor(
    private val repository: IUserRepository,
    private val dataStore: DataStorePreferencesManager
) : ITransactionsUseCase {
    override suspend fun invoke(date: Long): Result<List<UserBalanceResponseItem>> {
        return runCatching {
            val formattedDate = date.dateFormat()

            val token =
                dataStore.getUserToken() ?: throw AppExceptions.TokenNotFound("Token not found")

            val response = repository.getUserBalance(formattedDate, "Bearer $token")

            Log.d("GABRIEL", "response: ${response.body()}, $response")

            when {
                response.isSuccessful -> {
                    response.body() ?: throw AppExceptions.EmptyResponseException("Resposta vazia")
                }

                response.code() == 401 -> {
                    throw AppExceptions.UnauthorizedException(
                        "Sessão expirada. Faça login novamente"
                    )
                }

                else -> {
                    throw AppExceptions.UnknownException(
                        "Erro ${response.code()} - ${response.body()}"
                    )
                }
            }
        }.onSuccess {
            Log.d(LOG_TAG, "transacoes retornadas com sucesso $it")
        }.onFailure {
            Log.d(LOG_TAG, "erro $it")
        }
    }
}
