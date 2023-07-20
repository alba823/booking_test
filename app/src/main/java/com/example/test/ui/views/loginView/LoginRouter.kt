package com.example.test.ui.views.loginView

import androidx.navigation.NamedNavArgument
import com.example.test.ui.navigation.Router
import com.example.test.ui.navigation.buildRoute

class LoginRouter : Router() {
    override val authority = "login"
    override val route = buildRoute()

    override val arguments = listOf<NamedNavArgument>()
}