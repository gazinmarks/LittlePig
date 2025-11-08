package br.com.littlepig.data.repository

import br.com.littlepig.data.UserService
import br.com.littlepig.data.model.User
import br.com.littlepig.data.model.UserLoginRequest
import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.data.model.UserRegisterRequest
import br.com.littlepig.data.model.balance.Balance
import br.com.littlepig.data.model.balance.DeleteResponse
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val service: UserService
) : IUserRepository {
    override suspend fun registerUser(user: UserRegisterRequest): User = service.register(user)

    override suspend fun login(user: UserLoginRequest): UserLoginResponse = service.login(user)

    override suspend fun getUserBalance(
        date: String,
        token: String
    ): Response<List<UserBalanceResponseItem>> =
        service.getUserBalance(date, token)

    override suspend fun getAllTransactions(
        date: String,
        token: String
    ): Response<List<Balance>> =
        service.getAllTransactions(date, token)

    override suspend fun deleteTransaction(
        id: String,
        token: String
    ): Response<DeleteResponse> =
        service.deleteTransaction(id, token)
}
