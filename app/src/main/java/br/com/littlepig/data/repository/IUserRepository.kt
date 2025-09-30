package br.com.littlepig.data.repository

import br.com.littlepig.data.model.User

interface IUserRepository {
    suspend fun registerUser(user: User)
}