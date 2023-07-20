package com.example.test.ui.views.loginView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test.ui.navigation.Router
import com.example.test.ui.views.base.BaseView
import com.example.test.utils.EmptyCallback
import com.example.test.utils.GenericCallback
import com.example.test.utils.extensions.observeWithLifecycle
import com.example.test.viewModels.LoginViewModel
import com.example.test.viewModels.LoginViewModel.SignInStatus

object LoginView : BaseView<LoginViewModel, LoginViewCallbacks>() {

    override val router: Router = LoginRouter()

    @Composable
    override fun View(viewModel: LoginViewModel, callbacks: LoginViewCallbacks) {

        viewModel.signInStatus.observeWithLifecycle { result ->
            when (result) {
                is SignInStatus.EmailAndPasswordSuccess -> callbacks.onLoginEmailSucceed()
                is SignInStatus.Error -> callbacks.onLoginFailed(result.message)
            }
        }

        Content(
            email = viewModel.emailInput.value.value,
            password = viewModel.passwordInput.value.value,
            onEmailChanged = viewModel::changeEmail,
            onPasswordChanged = viewModel::changePassword,
            onLogInPressed = viewModel::signUpWithEmailAndPassword
        )
    }

    @Composable
    private fun Content(
        email: String,
        password: String,
        onEmailChanged: GenericCallback<String>,
        onPasswordChanged: GenericCallback<String>,
        onLogInPressed: EmptyCallback
    ) {
        Scaffold { scaffoldPaddings ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPaddings),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(text = "Log in screen")
                    TextField(value = email, onValueChange = onEmailChanged)
                    TextField(value = password, onValueChange = onPasswordChanged)
                    Button(onClick = onLogInPressed) {
                        Text(text = "Log In")
                    }
                }
            }
        }
    }

    @Composable
    override fun Preview(vararg args: Any) {
        Content(
            email = args[0] as? String ?: "",
            password = args[1] as? String ?: "",
            onEmailChanged = {},
            onPasswordChanged = {},
            onLogInPressed = {}
        )
    }
}

@Composable
@Preview
fun LoginViewPreview() = LoginView.Preview()