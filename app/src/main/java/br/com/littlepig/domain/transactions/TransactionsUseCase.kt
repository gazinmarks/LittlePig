package br.com.littlepig.domain.transactions

import android.util.Log
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import br.com.littlepig.data.repository.IUserRepository
import br.com.littlepig.domain.exceptions.AppExceptions
import br.com.littlepig.preferences.DataStorePreferencesManager
import br.com.littlepig.utils.Commons.KEY_USER_TOKEN
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class TransactionsUseCase @Inject constructor(
    private val repository: IUserRepository,
    private val dataStore: DataStorePreferencesManager
) : ITransactionsUseCase {
    override suspend fun invoke(date: Long): Result<List<UserBalanceResponseItem>> {
        return runCatching {
            val formattedDate = formatDate(date)

            val token = getUserToken() ?: throw AppExceptions.TokenNotFound("Token not found")

            val response = repository.getUserBalance(formattedDate, "Bearer $token")

            Log.d("GABRIEL", "response: ${response.body()}, $response")

            when {
                response.isSuccessful -> {
                    response.body()?.filter { transactions ->
                        !transactions.tag.contains("saldo", ignoreCase = true)
                    } ?: throw AppExceptions.EmptyResponseException("Resposta vazia")
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
            Log.d("tag", "transacoes retornadas com sucesso $it")
        }.onFailure {
            Log.d("tag", "erro $it")
        }
    }

    private fun formatDate(date: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return formatter.format(Date(date))
    }

    private suspend fun getUserToken(): String? = dataStore.read(KEY_USER_TOKEN)
}
