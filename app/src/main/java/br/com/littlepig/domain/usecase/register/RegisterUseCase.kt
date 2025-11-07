package br.com.littlepig.domain.usecase.register

import android.util.Log
import br.com.littlepig.data.model.User
import br.com.littlepig.data.model.UserRegisterRequest
import br.com.littlepig.data.repository.IUserRepository
import br.com.littlepig.utils.isFieldsInvalid
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: IUserRepository
) : IRegisterUseCase {
    override suspend fun invoke(fields: List<String>): Result<User> {
        return runCatching {
            if (fields.isFieldsInvalid()) {
                throw Exception()
            }
            val (name, password, email) = fields

            val user = UserRegisterRequest(name, password, email)

            repository.registerUser(user)
        }.onSuccess {
            Log.d(TAG, "User registered successfuly")
            Result.success(Unit)
        }.onFailure { exception ->
            Log.d(TAG, "Failure to register an user", exception)
            Result.failure<Exception>(exception)
        }
    }

    private companion object {
        const val TAG = "LITTLEPIG_LOG"
    }
}
