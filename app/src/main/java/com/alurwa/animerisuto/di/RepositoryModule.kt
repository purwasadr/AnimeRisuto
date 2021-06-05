package com.alurwa.animerisuto.di

import com.alurwa.animerisuto.data.AnimeRepository
import com.alurwa.animerisuto.data.IAnimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Purwa Shadr Al 'urwa on 04/05/2021
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAnimeRepository(
        animeRepository: AnimeRepository
    ): IAnimeRepository
}
