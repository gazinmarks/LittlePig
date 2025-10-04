package br.com.littlepig.data.repository

import br.com.littlepig.data.UserService
import br.com.littlepig.data.model.User
import br.com.littlepig.data.model.UserLoginRequest
import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.data.model.UserRegisterRequest
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val service: UserService
) : IUserRepository {
    override suspend fun registerUser(user: UserRegisterRequest): User =
        service.register(user)

    override suspend fun login(user: UserLoginRequest): UserLoginResponse =
        service.login(user)
}
