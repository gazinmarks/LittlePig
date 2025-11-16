package br.com.littlepig.domain.enums

import br.com.littlepig.common.Error

enum class DomainError : Error {
    TOKEN_NOT_FOUND,
    NO_TRANSACTION_VALUE,
    EMPTY_TRANSACTION_DESCRIPTION,
    EMPTY_TRANSACTION_DATE,
    UNAUTHORIZED,
    INVALID_FORMAT,
    EMPTY_RESPONSE,
    RESOURCE_NOT_FOUND,
    UNKNOWN,
    FIELD_INVALID
}
