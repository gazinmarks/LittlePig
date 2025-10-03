package br.com.littlepig.data.repository

import br.com.littlepig.data.model.User
import br.com.littlepig.data.model.UserLoginRequest
import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.data.model.UserRegisterRequest

interface IUserRepository {
    suspend fun registerUser(user: UserRegisterRequest): User

    suspend fun login(user: UserLoginRequest): UserLoginResponse
}
