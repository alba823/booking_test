package com.example.test.di

import com.example.test.data.auth.AuthRepository
import com.example.test.data.auth.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
annotation class FirebaseAuthInstance

@InstallIn(SingletonComponent::class)
@Module
object FirebaseInstanceModule {

    @FirebaseAuthInstance
    @Provides
    fun provideFirebaseAuthInstance(): FirebaseAuth = FirebaseAuth.getInstance()
}

@InstallIn(SingletonComponent::class)
@Module
abstract class FirebaseModule {

    @Binds
    abstract fun bindFirebaseAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository
}