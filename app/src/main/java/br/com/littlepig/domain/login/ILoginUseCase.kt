package br.com.littlepig.domain.login

import br.com.littlepig.data.model.UserLoginResponse

interface ILoginUseCase {
    suspend operator fun invoke(fields: List<String>): Result<UserLoginResponse>
}