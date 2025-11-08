package br.com.littlepig.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.littlepig.di.IoDispatcher
import br.com.littlepig.domain.exceptions.AppExceptions
import br.com.littlepig.domain.usecase.balance.IBalanceUseCase
import br.com.littlepig.domain.usecase.transactions.IDeleteTransactionUseCase
import br.com.littlepig.domain.usecase.transactions.IGetTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val balanceUseCase: IBalanceUseCase,
    private val transactionsUseCase: IGetTransactionsUseCase,
    private val deleteUseCase: IDeleteTransactionUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _transactions = MutableLiveData<UIState>() // TODO ver maneira de observar apenas um livedata
    val transactions: LiveData<UIState> = _transactions

    private val _balance = MutableLiveData<UIState>()
    val balance: LiveData<UIState> = _balance

    private val _deleteTransaction = MutableLiveData<UIState>()
    val deleteTransaction: LiveData<UIState> = _deleteTransaction

    private val scope = CoroutineScope(dispatcher)

    fun loadTransactions(date: Long) {
        scope.launch {
            transactionsUseCase.invoke(date).fold(
                onSuccess = { userListBalance ->
                    _transactions.postValue(UIState.Success(userListBalance))
                },
                onFailure = { exception ->
                    _transactions.postValue(mapExceptionsToUIState(exception))
                }
            )
        }
    }

    fun loadBalance(date: Long) = runCatching {
        scope.launch {
            balanceUseCase.invoke(date).fold(
                onSuccess = { listItems ->
                    _balance.postValue(UIState.Success(listItems))
                },
                onFailure = { exception ->
                    _balance.postValue(mapExceptionsToUIState(exception))
                }
            )
        }
    }

    fun deleteTransactionById(id: String) {
        scope.launch {
            deleteUseCase.invoke(id).fold(
                onSuccess = {
                    _deleteTransaction.postValue(UIState.Success(it.status))
                },
                onFailure = {
                    _deleteTransaction.postValue(mapExceptionsToUIState(it))
                }
            )
        }
    }

    private fun mapExceptionsToUIState(exception: Throwable): UIState {
        return when (exception) {
            is AppExceptions.EmptyResponseException ->
                UIState.Empty("Nenhuma transação encontrada.")

            is AppExceptions.UnauthorizedException -> {
                UIState.NotAuthenticated
            }

            is AppExceptions.TokenNotFound -> {
                UIState.TokenExpired
            }

            else -> {
                UIState.Error(error = exception.message ?: "Erro desconhecido")
            }
        }
    }

    sealed class UIState {
        data class Success<T>(val data: T) : UIState()
        data class Error(val error: String) : UIState()
        data class Empty(val message: String) : UIState()
        data object TokenExpired : UIState()
        data object NotAuthenticated : UIState()
    }
}
