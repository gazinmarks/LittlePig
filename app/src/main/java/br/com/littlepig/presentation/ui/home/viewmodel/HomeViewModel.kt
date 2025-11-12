package br.com.littlepig.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.littlepig.common.Result
import br.com.littlepig.common.RootError
import br.com.littlepig.di.IoDispatcher
import br.com.littlepig.domain.usecase.balance.IBalanceUseCase
import br.com.littlepig.domain.usecase.transactions.IDeleteTransactionUseCase
import br.com.littlepig.domain.usecase.transactions.IGetTransactionsUseCase
import br.com.littlepig.presentation.ErrorMapper
import br.com.littlepig.presentation.ui.UiText
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
    private val _transactions =
        MutableLiveData<UIState>() // TODO ver maneira de observar apenas um livedata
    val transactions: LiveData<UIState> = _transactions

    private val _balance = MutableLiveData<UIState>()
    val balance: LiveData<UIState> = _balance

    private val _deleteTransaction = MutableLiveData<UIState>()
    val deleteTransaction: LiveData<UIState> = _deleteTransaction

    private val scope = CoroutineScope(dispatcher)

    fun loadTransactions(date: Long) {
        scope.launch {
            when (val result = transactionsUseCase.invoke(date)) {
                is Result.Success -> {
                    _transactions.postValue(UIState.Success(result.data))
                }

                is Result.Error -> {
                    _transactions.postValue(
                        UIState.Error(
                            ErrorMapper.mapToUiText(result.error)
                        )
                    )
                }
            }
        }
    }

//    fun loadBalance(date: Long) = runCatching {
//        scope.launch {
//            balanceUseCase.invoke(date).fold(
//                onSuccess = { listItems ->
//                    _balance.postValue(UIState.Success(listItems))
//                },
//                onFailure = { exception ->
//                    _balance.postValue(ErrorMapper.mapToUiText)
//                }
//            )
//        }
//    }

    fun deleteTransactionById(id: String) {
        scope.launch {
            when (val result = deleteUseCase.invoke(id)) {
                is Result.Success -> _balance.postValue(UIState.Success(result.data))

                is Result.Error -> {
                    _balance.postValue(
                        UIState.Error(
                            ErrorMapper.mapToUiText(result.error)
                        )
                    )
                }
            }
        }
    }

    sealed class UIState {
        data class Success<T>(val data: T) : UIState()
        data class Error(val error: UiText) : UIState()
    }
}
