package br.com.littlepig.domain.exceptions

sealed class AppExceptions : Exception() {
    data class UnauthorizedException(override val message: String) : AppExceptions()
    data class EmptyResponseException(override val message: String) : AppExceptions()
    data class UnknownException(override val message: String) : AppExceptions()
    data class TokenNotFound(override val message: String) : AppExceptions()
    data class Error(override val message: String) : AppExceptions()
}
