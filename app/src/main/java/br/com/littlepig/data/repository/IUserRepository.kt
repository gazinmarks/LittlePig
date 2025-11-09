package br.com.littlepig.data.repository

import br.com.littlepig.data.model.User
import br.com.littlepig.data.model.UserLoginRequest
import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.data.model.UserRegisterRequest
import br.com.littlepig.data.model.balance.Balance
import br.com.littlepig.data.model.balance.DeleteResponse
import br.com.littlepig.data.model.balance.TransactionRequest
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import retrofit2.Response
import java.math.BigDecimal

interface IUserRepository {
    suspend fun registerUser(user: UserRegisterRequest): User

    suspend fun login(user: UserLoginRequest): UserLoginResponse

    suspend fun getUserBalance(date: String, token: String): Response<List<UserBalanceResponseItem>>

    suspend fun getAllTransactions(date: String, token: String): Response<List<Balance>>

    suspend fun deleteTransaction(id: String, token: String): Response<DeleteResponse>

    suspend fun createTransaction(
        transactionRequest: TransactionRequest,
        token: String
    ): Response<Balance>
}
