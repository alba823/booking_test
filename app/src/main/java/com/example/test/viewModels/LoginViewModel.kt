package com.example.test.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _emailInput = mutableStateOf(Input())
    val emailInput: State<Input> = _emailInput

    private val _passwordInput = mutableStateOf(Input())
    val passwordInput: State<Input> = _passwordInput

    private val _signInStatus = Channel<SignInStatus>()
    val signInStatus: Flow<SignInStatus> = _signInStatus.receiveAsFlow()

    private val statusesAreValid get() = _emailInput.value.isValid() && _passwordInput.value.isValid()

    fun changeEmail(newValue: String) {
        _emailInput.value = _emailInput.value.copy(value = newValue)
    }

    fun changePassword(newValue: String) {
        _passwordInput.value = _passwordInput.value.copy(value = newValue)
    }

    fun signUpWithEmailAndPassword() {
        _emailInput.value =
            _emailInput.value.copy(state = validateEmail(_emailInput.value.value))
        _passwordInput.value =
            _passwordInput.value.copy(state = validatePassword(_passwordInput.value.value))

        if (!statusesAreValid) return

        viewModelScope.launch {
            authRepository.signUpWithEmailAndPassword(
                emailInput.value.value,
                passwordInput.value.value
            ).collect { result ->
                result.onSuccess {
                    _signInStatus.send(SignInStatus.EmailAndPasswordSuccess)
                }.onFailure { e ->
                    _signInStatus.send(SignInStatus.Error(e.message ?: "Something went wrong"))
                }
            }
        }
    }

    private fun validateEmail(email: String): InputState {
        return when {
            email.isEmpty() -> InputState.Empty
            else -> InputState.Valid
        }
    }

    private fun validatePassword(password: String): InputState {
        return when {
            password.isEmpty() -> InputState.Empty
            else -> InputState.Valid
        }
    }

    sealed class SignInStatus {
        data class Error(val message: String): SignInStatus()
        object EmailAndPasswordSuccess: SignInStatus()
    }
}

//fun String.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

data class Input(
    var value: String = "",
    var state: InputState = InputState.Idle
) {
    fun isValid(): Boolean = state == InputState.Valid
}

enum class InputState {
    Idle, Valid, Invalid, Empty
}

