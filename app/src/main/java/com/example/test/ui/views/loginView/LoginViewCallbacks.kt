package com.example.test.ui.views.loginView

import com.example.test.ui.views.base.BaseCallbacks
import com.example.test.utils.EmptyCallback
import com.example.test.utils.GenericCallback

data class LoginViewCallbacks(
    val onLoginFailed: GenericCallback<String>,
    val onLoginEmailSucceed: EmptyCallback
): BaseCallbacks
