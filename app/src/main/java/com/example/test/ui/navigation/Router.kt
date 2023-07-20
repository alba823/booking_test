package com.example.test.ui.navigation

import android.net.Uri
import androidx.navigation.NamedNavArgument

abstract class Router {
    val baseRoute = "booking"

    abstract val authority: String
    abstract val route: String

    abstract val arguments: List<NamedNavArgument>
}

fun Router.buildRoute(
    path: String? = null,
    vararg arguments: String? = emptyArray()
): String {
    val builder = Uri.Builder()
        .scheme(baseRoute)
        .encodedAuthority(authority)

    if (!path.isNullOrEmpty()) {
        builder.appendEncodedPath(path)
    }

    arguments.forEach { item ->
        builder.appendQueryParameter(item, "{$item}")
    }
    return Uri.decode(builder.build().toString())
}