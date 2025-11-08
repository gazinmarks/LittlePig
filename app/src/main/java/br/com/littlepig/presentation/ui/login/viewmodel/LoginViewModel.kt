package br.com.littlepig.presentation.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.littlepig.di.IoDispatcher
import br.com.littlepig.domain.usecase.login.ILoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: ILoginUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _user = MutableLiveData<LoginState>()
    val user: LiveData<LoginState> = _user

    private val scope = CoroutineScope(dispatcher + Job())

    fun login(fields: List<String>) {
        scope.launch {
            useCase.invoke(fields)
                .fold(
                    onSuccess = {
                        _user.postValue(LoginState.Success)
                    },
                    onFailure = { exception ->
                        _user.postValue(LoginState.Failure(exception))
                    }
                )
        }
    }

    sealed class LoginState {
        data object Success : LoginState()
        data class Failure(val exception: Throwable) : LoginState()
    }
}
