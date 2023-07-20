package com.example.test.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.test.ui.views.loginView.LoginView
import com.example.test.ui.views.loginView.LoginViewCallbacks
import com.example.test.ui.views.testViews.TAG
import com.example.test.viewModels.LoginViewModel

@Composable
fun AppRoute(
    navHostController: NavHostController,
    startDestinationRoute: String = LoginView.router.route
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestinationRoute,
    ) {
        with(LoginView) {
            composable(
                route = router.route,
                arguments = router.arguments
            ) {
                val viewModel = hiltViewModel<LoginViewModel>()

                View(
                    viewModel = viewModel,
                    callbacks = LoginViewCallbacks(
                        onLoginFailed = { message ->
                            Log.i(TAG, "Failed: $message")
                        },
                        onLoginEmailSucceed = {
                            Log.i(TAG, "Succeed")
                        }
                    )
                )
            }
        }
    }
}