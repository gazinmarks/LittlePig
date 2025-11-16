package br.com.littlepig.common

import br.com.littlepig.data.enums.NetworkError
import br.com.littlepig.domain.enums.DomainError
import retrofit2.HttpException

inline fun <D, E : RootError, R : RootError> Result<D, E>.mapError(block: (E) -> R): Result<D, R> {
    return when (this) {
        is Result.Success -> Result.Success(this.data)
        is Result.Error -> Result.Error(block(this.error))
    }
}

fun <T> mapToDomainErrors(error: T): DomainError {
    return when (error) {
        NetworkError.UNAUTHORIZED -> DomainError.UNAUTHORIZED
        NetworkError.BAD_REQUEST -> DomainError.INVALID_FORMAT
        NetworkError.EMPTY_RESPONSE -> DomainError.EMPTY_RESPONSE
        NetworkError.NOT_FOUND -> DomainError.RESOURCE_NOT_FOUND
        else -> DomainError.UNKNOWN
    }
}

fun HttpException.asResultError(): Result<Nothing, NetworkError> {
    return when (this.code()) {
        400 -> Result.Error(NetworkError.BAD_REQUEST)

        401 -> Result.Error(NetworkError.UNAUTHORIZED)

        404 -> Result.Error(NetworkError.NOT_FOUND)

        else -> Result.Error(NetworkError.UNKNOWN)
    }
}
