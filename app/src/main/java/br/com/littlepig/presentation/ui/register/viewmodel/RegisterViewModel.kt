package br.com.littlepig.presentation.ui.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.littlepig.di.IoDispatcher
import br.com.littlepig.domain.register.IRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
            _user.value = State.Loading

            useCase.invoke(fields)
                .fold(
                    onSuccess = {
                        _user.postValue(State.Success)
                    },
                    onFailure = { exception ->
                        _user.postValue(State.Failure(exception))
                    }
                )
        }
    }

    sealed class State {
        data class Failure(val error: Throwable) : State()
        data object Success : State()
        data object Loading : State()
    }
}
