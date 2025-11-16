package br.com.littlepig.domain.usecase.register

import br.com.littlepig.common.Result
import br.com.littlepig.data.model.User
import br.com.littlepig.domain.enums.DomainError

interface IRegisterUseCase {
    suspend operator fun invoke(fields: List<String>): Result<User, DomainError>
}