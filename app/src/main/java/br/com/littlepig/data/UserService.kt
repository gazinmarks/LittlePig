package br.com.littlepig.data

import br.com.littlepig.data.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/users")
    suspend fun register(@Body user: User)

//    @POST("/login")
//    suspend fun login(): Boolean

//    @GET("/me")
//    suspend fun getUserDetails()
}