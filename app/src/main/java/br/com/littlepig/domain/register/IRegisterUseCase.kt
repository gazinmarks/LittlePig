package br.com.littlepig.domain.register

import br.com.littlepig.data.model.User

interface IRegisterUseCase {
    suspend operator fun invoke(fields: List<String>): Result<User>
}