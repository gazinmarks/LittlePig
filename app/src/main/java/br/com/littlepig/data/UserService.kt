package br.com.littlepig.data

import retrofit2.http.POST

interface UserService {
    @POST("/users")
    suspend fun register(name: String, password: String, email: String)

//    @POST("/login")
//    suspend fun login(): Boolean

//    @GET("/me")
//    suspend fun getUserDetails()
}