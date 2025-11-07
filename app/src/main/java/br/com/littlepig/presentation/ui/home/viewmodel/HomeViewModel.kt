package br.com.littlepig.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.littlepig.di.IoDispatcher
import br.com.littlepig.domain.exceptions.AppExceptions
import br.com.littlepig.domain.usecase.transactions.ITransactionsUseCase
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
    private val _transactions = MutableLiveData<UIState>()
    val transactions: LiveData<UIState> = _transactions

    private val _balance = MutableLiveData<UIState>()
    val balance: LiveData<UIState> = _balance

    private val scope = CoroutineScope(dispatcher)

    fun loadTransactions(date: Long) {
        scope.launch {
            useCase.invoke(date).fold(
                onSuccess = { userListBalance ->
                    val itemsFiltered = userListBalance.filter { transactions ->
                        !transactions.tag.contains("saldo")
                    }

                    _transactions.postValue(UIState.Success(itemsFiltered))
                },
                onFailure = { exception ->
                    _transactions.postValue(mapExceptionsToUIState(exception))
                }
            )
        }
    }

    fun loadBalance(date: Long) = runCatching {
        scope.launch {
            useCase.invoke(date).fold(
                onSuccess = { listItems ->
                    val itemsFiltered = listItems.filter { currentBalance ->
                        currentBalance.tag.contains("saldo")
                    }

                    _balance.postValue(UIState.Success(itemsFiltered))
                },
                onFailure = { exception ->
                    _balance.postValue(mapExceptionsToUIState(exception))
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
        data class Success<T>(val data: List<T>) : UIState()
        data class Error(val error: String) : UIState()
        data class Empty(val message: String) : UIState()
        data object TokenExpired : UIState()
        data object NotAuthenticated : UIState()
    }
}
