package br.com.littlepig.domain.usecase.login

import br.com.littlepig.common.Result
import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.domain.enums.DomainError

interface ILoginUseCase {
    suspend operator fun invoke(fields: List<String>): Result<UserLoginResponse, DomainError>
}
