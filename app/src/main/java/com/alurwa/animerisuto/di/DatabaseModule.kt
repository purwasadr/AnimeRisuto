package com.alurwa.animerisuto.di

import android.content.Context
import androidx.room.Room
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.local.room.dao.AnimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Purwa Shadr Al 'urwa on 15/05/2021
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AnimeRisutoDatabase =
        Room.databaseBuilder(
            context,
            AnimeRisutoDatabase::class.java, "AnimeRisuto.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideTourismDao(database: AnimeRisutoDatabase): AnimeDao =
        database.animeDao()
}
