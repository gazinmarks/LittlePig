package br.com.littlepig.domain.usecase.register

import br.com.littlepig.common.Result
import br.com.littlepig.common.mapError
import br.com.littlepig.common.mapToDomainErrors
import br.com.littlepig.data.model.User
import br.com.littlepig.data.model.UserRegisterRequest
import br.com.littlepig.data.repository.user.IUserRepository
import br.com.littlepig.domain.enums.DomainError
import br.com.littlepig.utils.isFieldsInvalid
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: IUserRepository
) : IRegisterUseCase {
    override suspend fun invoke(fields: List<String>): Result<User, DomainError> {
        if (fields.isFieldsInvalid()) {
            return Result.Error(DomainError.FIELD_INVALID)
        }
        val (name, password, email) = fields

        val user = UserRegisterRequest(name, password, email)

        return repository.registerUser(user).mapError {
            mapToDomainErrors(it)
        }
    }
}
