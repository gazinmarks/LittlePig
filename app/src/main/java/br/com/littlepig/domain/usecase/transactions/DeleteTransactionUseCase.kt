package br.com.littlepig.domain.usecase.transactions

import android.util.Log
import br.com.littlepig.data.model.balance.DeleteResponse
import br.com.littlepig.data.repository.IUserRepository
import br.com.littlepig.domain.exceptions.AppExceptions
import br.com.littlepig.preferences.IDataStorePreferences
import br.com.littlepig.utils.Commons.KEY_USER_TOKEN
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: IUserRepository,
    private val dataStore: IDataStorePreferences
) : IDeleteTransactionUseCase {
    override suspend fun invoke(id: String): Result<DeleteResponse> = runCatching {
        val token =
            dataStore.read(KEY_USER_TOKEN) ?: throw AppExceptions.TokenNotFound("Token not found")

        val response = repository.deleteTransaction(id, "Bearer $token")

        when {
            response.isSuccessful -> {
                response.body()
                    ?: throw AppExceptions.Error("Erro ${response.code()} - ${response.body()}")
            }

            else -> {
                throw AppExceptions.UnknownException("")
            }
        }
    }.onSuccess {
        Log.d("log", "sucesso delete $it")
    }.onFailure {
        Log.d("log", "erro delete $it")
    }
}
