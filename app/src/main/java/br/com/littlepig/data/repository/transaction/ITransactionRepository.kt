package br.com.littlepig.data.repository.transaction

import br.com.littlepig.common.Result
import br.com.littlepig.data.enums.NetworkError
import br.com.littlepig.data.model.balance.Balance
import br.com.littlepig.data.model.balance.DeleteResponse
import br.com.littlepig.data.model.balance.TransactionRequest

interface ITransactionRepository {
    suspend fun getAllTransactions(date: String, token: String): Result<List<Balance>, NetworkError>

    suspend fun deleteTransaction(id: String, token: String): Result<DeleteResponse, NetworkError>

    suspend fun createTransaction(
        transactionRequest: TransactionRequest,
        token: String
    ): Result<Balance, NetworkError>
}
