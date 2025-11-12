package br.com.littlepig.presentation

import br.com.littlepig.R
import br.com.littlepig.common.RootError
import br.com.littlepig.domain.enums.DomainError
import br.com.littlepig.presentation.ui.UiText
import br.com.littlepig.presentation.ui.UiText.StringResource

object ErrorMapper {
    fun mapToUiText(throwable: RootError): UiText {
        return when (throwable) {
            is DomainError -> when (throwable) {
                DomainError.INVALID_FORMAT -> StringResource(R.string.error_invalid_data)
                DomainError.UNAUTHORIZED -> StringResource(R.string.error_unauthorized)
                DomainError.RESOURCE_NOT_FOUND -> StringResource(R.string.error_not_found)
                DomainError.EMPTY_RESPONSE -> StringResource(R.string.error_empty_response)
                DomainError.UNKNOWN -> StringResource(R.string.error_network)
                DomainError.TOKEN_NOT_FOUND -> StringResource(R.string.error_not_found)
                DomainError.NO_TRANSACTION_VALUE -> StringResource(R.string.no_transaction_value)
                DomainError.EMPTY_TRANSACTION_DESCRIPTION -> StringResource(R.string.empty_transaction_description)
                DomainError.EMPTY_TRANSACTION_DATE -> StringResource(R.string.empty_transaction_date)
            }
            else -> StringResource(R.string.error_generic)
        }
    }
}
