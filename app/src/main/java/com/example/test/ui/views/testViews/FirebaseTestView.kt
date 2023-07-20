package com.example.test.ui.views.testViews

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import com.example.test.data.auth.AuthRepository
import kotlinx.coroutines.launch

@Composable
fun FirebaseTestView(test: AuthRepository) {
    val scope = rememberCoroutineScope()

    Box(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                scope.launch {
                    test.signUpWithEmailAndPassword("test@gmail.com", "Testpass1")
                        .collect { result ->
                            result.onSuccess {
                                Log.i(TAG, "Successfully Signed Up")
                            }.onFailure { e ->
                                Log.i(TAG, "Error: $e")
                            }
                        }
                }
            }) {
                Text(text = "Sign Up")
            }
            Button(onClick = {
                scope.launch {
                    test.signInWithEmailAndPassword("test@gmail.com", "Testpass1")
                        .collect { result ->
                            result.onSuccess {
                                Log.i(TAG, "Successfully Signed In")
                            }.onFailure { e ->
                                Log.i(TAG, "Error: $e")
                            }
                        }
                }
            }) {
                Text(text = "Log In")
            }
            Button(onClick = {
                Log.i(TAG, "User: ${test.currentUser}")
            }) {
                Text(text = "Check User")
            }
            Button(onClick = {
                test.signOut()
                Log.i(TAG, "Signed Out")
            }) {
                Text(text = "Sign Out")
            }
        }
    }
}

const val TAG = "Investigation"