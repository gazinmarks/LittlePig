package br.com.littlepig.data.repository.transaction

import br.com.littlepig.common.Result
import br.com.littlepig.common.asResultError
import br.com.littlepig.data.UserService
import br.com.littlepig.data.enums.NetworkError
import br.com.littlepig.data.model.balance.Balance
import br.com.littlepig.data.model.balance.DeleteResponse
import br.com.littlepig.data.model.balance.TransactionRequest
import retrofit2.HttpException
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val service: UserService
) : ITransactionRepository {
    override suspend fun getAllTransactions(
        date: String,
        token: String
    ): Result<List<Balance>, NetworkError> {
        return try {
            val response = service.getAllTransactions(date, token)

            response.body()?.let { balanceList ->
                Result.Success(balanceList)
            } ?: Result.Error(NetworkError.EMPTY_RESPONSE)
        } catch (error: HttpException) {
            error.asResultError()
        }
    }

    override suspend fun deleteTransaction(
        id: String,
        token: String
    ): Result<DeleteResponse, NetworkError> {
        return try {
            val response = service.deleteTransaction(id, token)

            response.body()?.let {
                Result.Success(it)
            } ?: Result.Error(NetworkError.EMPTY_RESPONSE)
        } catch (error: HttpException) {
            error.asResultError()
        }

    }

    override suspend fun createTransaction(
        transactionRequest: TransactionRequest, token: String
    ): Result<Balance, NetworkError> {
        return try {
            val response = service.createTransaction(transactionRequest, token)

            response.body()?.let { balance ->
                Result.Success(balance)
            } ?: Result.Error(NetworkError.EMPTY_RESPONSE)
        } catch (error: HttpException) {
            error.asResultError()
        }
    }
}
