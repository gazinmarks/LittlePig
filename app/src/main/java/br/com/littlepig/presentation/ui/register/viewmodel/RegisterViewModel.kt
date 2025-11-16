package br.com.littlepig.presentation.ui.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.littlepig.common.Result
import br.com.littlepig.di.IoDispatcher
import br.com.littlepig.domain.usecase.register.IRegisterUseCase
import br.com.littlepig.presentation.ErrorMapper
import br.com.littlepig.presentation.ui.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: IRegisterUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private var _user = MutableLiveData<State>()
    val user: LiveData<State> = _user
    private val scope = CoroutineScope(dispatcher + Job())

    fun handleUser(fields: List<String>) {
        scope.launch {
            _user.postValue(State.Loading)

            when (val result = useCase.invoke(fields)) {
                is Result.Success -> {
                    _user.postValue(
                        State.Success(
                            UiText.DynamicResource("Conta criada com sucesso")
                        )
                    )
                }

                is Result.Error -> {
                    _user.postValue(
                        State.Failure(
                            ErrorMapper.mapToUiText(result.error)
                        )
                    )
                }
            }
        }
    }

    sealed class State {
        data class Success<T>(val data: T) : State()
        data class Failure(val error: UiText) : State()
        data object Loading : State()
    }
}
