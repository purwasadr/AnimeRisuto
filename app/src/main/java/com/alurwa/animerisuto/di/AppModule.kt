package com.alurwa.animerisuto.di

import android.content.Context
import com.alurwa.animerisuto.constant.OAUTH_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by Purwa Shadr Al 'urwa on 20/06/2021
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @DispatchersIO
    @Singleton
    @Provides
    fun provideDispatchers() = Dispatchers.IO

    @Provides
    @OAuthSharedPreferences
    fun provideOauthSharedPreferences(
        @ApplicationContext context: Context
    ) = context.getSharedPreferences(
        OAUTH_PREFERENCES,
        Context.MODE_PRIVATE
    )

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OAuthSharedPreferences

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatchersIO

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope