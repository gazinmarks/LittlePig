package br.com.littlepig.data.enums

import br.com.littlepig.common.Error

enum class NetworkError : Error {
    BAD_REQUEST,
    UNAUTHORIZED,
    NOT_FOUND,
    EMPTY_RESPONSE,
    UNKNOWN,
}
