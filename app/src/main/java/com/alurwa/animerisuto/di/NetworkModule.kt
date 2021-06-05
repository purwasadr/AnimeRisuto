package com.alurwa.animerisuto.di

import android.content.Context
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.data.source.remote.network.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Purwa Shadr Al 'urwa on 04/05/2021
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoginService(): LoginService = LoginService.create()

    @Singleton
    @Provides
    fun provideApiService(
        @ApplicationContext context: Context,
        loginService: LoginService
    ): ApiService = ApiService.create(context, loginService)
}
