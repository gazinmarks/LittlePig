package br.com.littlepig.data.repository

import br.com.littlepig.data.UserService
import br.com.littlepig.data.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val service: UserService
) : IUserRepository {
    override suspend fun registerUser(user: User) {
        service.register(user)
    }
}
