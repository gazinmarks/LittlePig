package br.com.littlepig.presentation.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.littlepig.common.Result
import br.com.littlepig.di.IoDispatcher
import br.com.littlepig.domain.usecase.login.ILoginUseCase
import br.com.littlepig.presentation.ErrorMapper
import br.com.littlepig.presentation.ui.UiText
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
            when (val result = useCase.invoke(fields)) {
                is Result.Success -> {
                    _user.postValue(
                        LoginState.Success(
                            UiText.DynamicResource("Seja bem vindo!")
                        )
                    )
                }

                is Result.Error -> {
                    _user.postValue(
                        LoginState.Failure(
                            ErrorMapper.mapToUiText(result.error)
                        )
                    )
                }
            }
        }
    }

    sealed class LoginState {
        data class Success<T>(val data: T) : LoginState()
        data class Failure(val exception: UiText) : LoginState()
    }
}
