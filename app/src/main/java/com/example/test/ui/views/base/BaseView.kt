package com.example.test.ui.views.base

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.test.ui.navigation.Router

abstract class BaseView<T: ViewModel, S: BaseCallbacks> {

    abstract val router: Router

    @Composable
    abstract fun View(viewModel: T, callbacks: S)

    @Composable
    abstract fun Preview(vararg args: Any)
}