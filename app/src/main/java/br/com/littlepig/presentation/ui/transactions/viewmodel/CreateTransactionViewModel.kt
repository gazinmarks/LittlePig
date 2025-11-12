package br.com.littlepig.presentation.ui.transactions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.littlepig.common.Result
import br.com.littlepig.di.IoDispatcher
import br.com.littlepig.domain.usecase.transactions.ICreateTransactionUseCase
import br.com.littlepig.presentation.ErrorMapper
import br.com.littlepig.presentation.ui.UiText
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
        value: String,
        type: String,
        date: Long
    ) {
        scope.launch {
            //TODO converter value de string para bigdecimal
            when (val result = useCase.invoke(description, value, type, date)) {
                is Result.Error -> {
                    _newTransaction.postValue(
                        State.Error(
                            ErrorMapper.mapToUiText(result.error)
                        )
                    )
                }

                is Result.Success -> {
                    _newTransaction.postValue(State.Success(result.data))
                }
            }
        }
    }

    sealed class State {
        data class Success<T>(val data: T) : State()
        data class Error(val message: UiText) : State()
        data class ValueEmpty(val message: UiText) : State()
    }
}
