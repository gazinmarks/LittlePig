package br.com.littlepig.domain

import android.util.Log
import br.com.littlepig.data.model.User
import br.com.littlepig.data.repository.IUserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: IUserRepository
) : IRegisterUseCase {
    override suspend fun invoke(fields: List<String>): Result<Unit> {
        return runCatching {
            if (isFieldsInvalid(fields)) {
                throw Exception()
            }
            val (name, password, email) = fields

            val user = User(name, password, email)

            repository.registerUser(user)
        }.onSuccess {
            Log.d(TAG, "User registered successfuly")
            Result.success(Unit)
        }.onFailure { exception ->
            Log.d(TAG, "Failure to register an user", exception)
            Result.failure<Exception>(exception)
        }
    }

    private fun isFieldsInvalid(fields: List<String>): Boolean = fields.any { it.isEmpty() }

    private companion object {
        const val TAG = "LITTLEPIG_LOG"
    }
}
