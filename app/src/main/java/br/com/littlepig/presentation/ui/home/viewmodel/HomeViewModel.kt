package br.com.littlepig.presentation.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import br.com.littlepig.di.IoDispatcher
import br.com.littlepig.domain.exceptions.AppExceptions
import br.com.littlepig.domain.transactions.ITransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: ITransactionsUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _transactions = MutableLiveData<TransactionState>()
    val transactions: LiveData<TransactionState> = _transactions
    private val scope = CoroutineScope(dispatcher)

    fun loadTransactions(date: Long) {
        scope.launch {
            useCase.invoke(date).fold(
                onSuccess = { userListBalance ->
                    Log.d(
                        "GABRIEL",
                        "entrei no onSuccess da viewModel, enviando list $userListBalance"
                    )
                    _transactions.postValue(TransactionState.Success(userListBalance))
                },
                onFailure = { exception ->
                    Log.d(
                        "GABRIEL",
                        "entrei no onFailure da viewModel, enviando exception $exception"
                    )
                    _transactions.postValue(mapExceptionsToUIState(exception))
                }
            )
        }
    }

    private fun mapExceptionsToUIState(exception: Throwable): TransactionState {
        return when (exception) {
            is AppExceptions.EmptyResponseException ->
                TransactionState.Empty("Nenhuma transação encontrada.")

            is AppExceptions.UnauthorizedException -> {
                TransactionState.NotAuthenticated
            }

            is AppExceptions.TokenNotFound -> {
                TransactionState.TokenExpired
            }

            else -> {
                TransactionState.Error(error = exception.message ?: "Erro desconhecido")
            }
        }
    }

    sealed class TransactionState {
        data class Success(val transactions: List<UserBalanceResponseItem>) : TransactionState()
        data class Error(val error: String) : TransactionState()
        data class Empty(val message: String) : TransactionState()
        data object TokenExpired : TransactionState()
        data object NotAuthenticated : TransactionState()
    }
}
