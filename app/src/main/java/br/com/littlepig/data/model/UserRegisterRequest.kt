package br.com.littlepig.data.model

data class UserRegisterRequest(
    val name: String,
    val password: String,
    val email: String
)
