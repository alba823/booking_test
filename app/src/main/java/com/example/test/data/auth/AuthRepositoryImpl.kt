package com.example.test.data.auth

import com.example.test.di.FirebaseAuthInstance
import com.example.test.utils.AnyResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @FirebaseAuthInstance private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AnyResult> {
        return flow {
            runCatching {
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            }.onSuccess {
                emit(Result.success(null))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AnyResult> {
        return flow {
            kotlin.runCatching {
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
            }.onSuccess {
                emit(Result.success(null))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }

    override fun signOut() = firebaseAuth.signOut()
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser
}