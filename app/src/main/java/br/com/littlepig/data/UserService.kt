package br.com.littlepig.data

import br.com.littlepig.data.model.User
import br.com.littlepig.data.model.UserLoginRequest
import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.data.model.UserRegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/users")
    suspend fun register(@Body user: UserRegisterRequest): User

    @POST("/login")
    suspend fun login(@Body user: UserLoginRequest): UserLoginResponse

//    @GET("/me")
//    suspend fun getUserDetails()
}