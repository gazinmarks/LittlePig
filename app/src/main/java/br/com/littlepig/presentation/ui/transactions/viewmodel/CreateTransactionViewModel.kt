package br.com.littlepig.presentation.ui.transactions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.littlepig.di.IoDispatcher
import br.com.littlepig.domain.usecase.transactions.ICreateTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class CreateTransactionViewModel @Inject constructor(
    private val useCase: ICreateTransactionUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _newTransaction = MutableLiveData<State>()
    val newTransaction: LiveData<State> = _newTransaction

    private val scope = CoroutineScope(dispatcher)

    fun createTransaction(
        description: String,
        value: BigDecimal,
        type: String,
        date: Long
    ) {
        scope.launch {
            useCase.invoke(description, value, type, date).fold(
                onSuccess = {
                    _newTransaction.postValue(State.Success(it))
                },
                onFailure = {
                    _newTransaction.postValue(State.Error(it))
                }
            )
        }
    }

    sealed class State {
        data class Success<T>(val data: T) : State()
        data class Error(val exception: Throwable) : State()
    }
}
