package br.com.littlepig.data.repository.user

import br.com.littlepig.common.Result
import br.com.littlepig.data.enums.NetworkError
import br.com.littlepig.data.model.User
import br.com.littlepig.data.model.UserLoginRequest
import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.data.model.UserRegisterRequest
import br.com.littlepig.data.model.balance.UserBalanceResponseItem

interface IUserRepository {
    suspend fun registerUser(user: UserRegisterRequest): Result<User, NetworkError>

    suspend fun login(user: UserLoginRequest): Result<UserLoginResponse, NetworkError>

    suspend fun getUserBalance(
        date: String,
        token: String
    ): Result<List<UserBalanceResponseItem>, NetworkError>
}
