package br.com.littlepig.data.repository.user

import br.com.littlepig.common.Result
import br.com.littlepig.common.asResultError
import br.com.littlepig.data.UserService
import br.com.littlepig.data.enums.NetworkError
import br.com.littlepig.data.model.User
import br.com.littlepig.data.model.UserLoginRequest
import br.com.littlepig.data.model.UserLoginResponse
import br.com.littlepig.data.model.UserRegisterRequest
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import retrofit2.HttpException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val service: UserService
) : IUserRepository {
    override suspend fun registerUser(user: UserRegisterRequest): Result<User, NetworkError> {
        return try {
            val response = service.register(user)

            response.body()?.let { userResponse ->
                Result.Success(userResponse)
            } ?: Result.Error(NetworkError.EMPTY_RESPONSE)
        } catch (error: HttpException) {
            error.asResultError()
        }
    }

    override suspend fun login(user: UserLoginRequest): Result<UserLoginResponse, NetworkError> {
        return try {
            val response = service.login(user)

            response.body()?.let { userLoginResponse ->
                Result.Success(userLoginResponse)
            } ?: Result.Error(NetworkError.EMPTY_RESPONSE)
        } catch (error: HttpException) {
            error.asResultError()
        }
    }

    override suspend fun getUserBalance(
        date: String,
        token: String
    ): Result<List<UserBalanceResponseItem>, NetworkError> {
        return try {
            val response = service.getUserBalance(date, token)

            response.body()?.let { userBalance ->
                Result.Success(userBalance)
            } ?: return Result.Error(NetworkError.EMPTY_RESPONSE)
        } catch (error: HttpException) {
            error.asResultError()
        }
    }
}
