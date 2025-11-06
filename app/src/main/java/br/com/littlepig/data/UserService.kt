package br.com.littlepig.data

import br.com.littlepig.data.model.User
import br.com.littlepig.data.model.UserLoginRequest
import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.data.model.UserRegisterRequest
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("/users")
    suspend fun register(@Body user: UserRegisterRequest): User

    @POST("/login")
    suspend fun login(@Body user: UserLoginRequest): UserLoginResponse

    @GET("/balance")
    suspend fun getUserBalance(
        @Query("dd/mm/yyyy") date: String,
        @Header("Authorization") token: String
    ): Response<List<UserBalanceResponseItem>>
}