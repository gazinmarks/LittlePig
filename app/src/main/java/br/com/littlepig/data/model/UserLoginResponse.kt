package br.com.littlepig.data.model

data class UserLoginResponse(
    val user: User,
    val token: String
)
