package br.com.littlepig.domain.usecase.login

import androidx.annotation.VisibleForTesting
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.littlepig.common.Result
import br.com.littlepig.common.mapError
import br.com.littlepig.common.mapToDomainErrors
import br.com.littlepig.data.model.UserLoginRequest
import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.data.repository.user.IUserRepository
import br.com.littlepig.domain.enums.DomainError
import br.com.littlepig.preferences.IDataStorePreferences
import br.com.littlepig.utils.isFieldsInvalid
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: IUserRepository,
    private val dataStore: IDataStorePreferences
) : ILoginUseCase {
    override suspend fun invoke(fields: List<String>): Result<UserLoginResponse, DomainError> {
        if (fields.isFieldsInvalid()) {
            return Result.Error(DomainError.FIELD_INVALID)
        }

        val (email, password) = fields

        val user = UserLoginRequest(email, password)

        val result = repository.login(user)

        val token = when (result is Result.Success) {
            true -> result.data.token
            false -> EMPTY_TOKEN
        }

        if (validateIfTokenExists(token)) {
            dataStore.write(KEY_USER_TOKEN, token)
        }

        return result.mapError {
            mapToDomainErrors(it)
        }
    }

    private fun validateIfTokenExists(token: String): Boolean = token.isNotEmpty()

    companion object {
        @VisibleForTesting
        val KEY_USER_TOKEN = stringPreferencesKey("USER_TOKEN")
        private const val EMPTY_TOKEN = ""
    }
}
