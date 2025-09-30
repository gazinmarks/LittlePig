package br.com.littlepig.presentation.ui.register.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.littlepig.domain.IRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: IRegisterUseCase
) : ViewModel() {
    private var _user = MutableLiveData<State>()
    val user: LiveData<State> = _user
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    fun handleUser(fields: List<String>) {
        coroutineScope.launch {
            _user.value = State.Loading

            useCase.invoke(fields)
                .fold(
                    onSuccess = {
                        Log.d("GABRIEL", "Result viewModel: $it")
                        _user.postValue(State.Success)
                    },
                    onFailure = { exception ->
                        Log.d("GABRIEL", "Exception viewModel: $exception")
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