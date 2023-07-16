package com.example.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.test.data.auth.AuthRepository
import com.example.test.ui.theme.TestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var test: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                // A surface container using the 'background' color from the theme
                Scaffold { padding ->
                    Box(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(onClick = {
                                lifecycleScope.launch {
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
                                lifecycleScope.launch {
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
            }
        }
    }
}

const val TAG = "Investigation"