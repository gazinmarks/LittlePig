package br.com.littlepig.domain.usecase.login

import androidx.annotation.VisibleForTesting
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.littlepig.data.model.UserLoginRequest
import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.data.repository.user.IUserRepository
import br.com.littlepig.preferences.IDataStorePreferences
import br.com.littlepig.utils.isFieldsInvalid
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: IUserRepository,
    private val dataStore: IDataStorePreferences
) : ILoginUseCase {
    override suspend fun invoke(fields: List<String>): Result<UserLoginResponse> = runCatching {
        if (fields.isFieldsInvalid()) {
            throw Exception()
        }

        val (email, password) = fields

        val user = UserLoginRequest(email, password)

        val result = repository.login(user)

        if (validateIfTokenExists(result.token)) {
            dataStore.write(KEY_USER_TOKEN, result.token)
        }

        result
    }.onSuccess { userResponse ->
        Result.success(userResponse)
    }.onFailure { exception ->
        Result.failure<Exception>(exception)
    }

    private fun validateIfTokenExists(token: String): Boolean = token.isNotEmpty()

    companion object {
        @VisibleForTesting
        val KEY_USER_TOKEN = stringPreferencesKey("USER_TOKEN")
    }
}
