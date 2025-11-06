package br.com.littlepig.data.repository

import br.com.littlepig.data.model.User
import br.com.littlepig.data.model.UserLoginRequest
import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.data.model.UserRegisterRequest
import br.com.littlepig.data.model.balance.BalanceResponse
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import retrofit2.Response

interface IUserRepository {
    suspend fun registerUser(user: UserRegisterRequest): User

    suspend fun login(user: UserLoginRequest): UserLoginResponse

    suspend fun getUserBalance(date: String, token: String): Response<List<UserBalanceResponseItem>>
}
