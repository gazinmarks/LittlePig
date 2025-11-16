package br.com.littlepig.domain.usecase.balance

import br.com.littlepig.common.Result
import br.com.littlepig.common.mapError
import br.com.littlepig.common.mapToDomainErrors
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import br.com.littlepig.data.repository.user.IUserRepository
import br.com.littlepig.domain.enums.DomainError
import br.com.littlepig.preferences.DataStorePreferencesManager
import br.com.littlepig.utils.dateFormat
import br.com.littlepig.utils.getUserToken
import javax.inject.Inject

class BalanceUseCase @Inject constructor(
    private val repository: IUserRepository,
    private val dataStore: DataStorePreferencesManager
) : IBalanceUseCase {
    override suspend fun invoke(date: Long): Result<List<UserBalanceResponseItem>, DomainError> {
        val formattedDate = date.dateFormat()

        val token =
            dataStore.getUserToken() ?: return Result.Error(DomainError.TOKEN_NOT_FOUND)

        if (formattedDate.isBlank()) {
            return Result.Error(DomainError.EMPTY_TRANSACTION_DATE)
        }

        val response = repository.getUserBalance(formattedDate, "Bearer $token")

        return response.mapError {
            mapToDomainErrors(it)
        }
    }
}
