package com.example.test.data.auth

import com.example.test.utils.AnyResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Flow<AnyResult>
    suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<AnyResult>
    fun signOut()
    val currentUser: FirebaseUser?
}