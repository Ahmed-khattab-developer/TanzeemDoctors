package com.tanzeem.tanzeemDoctors.di.module

import android.content.Context
import com.tanzeem.tanzeemDoctors.repository.AuthRepository
import com.tanzeem.tanzeemDoctors.repository.ConnectivityRepository
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context) = appContext

    @Provides
    fun provideAuthRepository() = AuthRepository()

    @Provides
    fun provideConnectivityRepository(@ApplicationContext context: Context) =
        ConnectivityRepository(context)

}