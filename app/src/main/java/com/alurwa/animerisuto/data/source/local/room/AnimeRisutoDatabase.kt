package com.alurwa.animerisuto.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeRankingKeyEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeSuggestionKeyEntity
import com.alurwa.animerisuto.data.source.local.entity.MangaEntity
import com.alurwa.animerisuto.data.source.local.entity.MangaRemoteKeysEntity
import com.alurwa.animerisuto.data.source.local.entity.UserEntity
import com.alurwa.animerisuto.data.source.local.room.dao.AnimeDao
import com.alurwa.animerisuto.data.source.local.room.dao.AnimeDetailDao
import com.alurwa.animerisuto.data.source.local.room.dao.AnimeRankingKeyDao
import com.alurwa.animerisuto.data.source.local.room.dao.AnimeSuggestionKeyDao
import com.alurwa.animerisuto.data.source.local.room.dao.MangaDao
import com.alurwa.animerisuto.data.source.local.room.dao.MangaRemoteKeysDao
import com.alurwa.animerisuto.data.source.local.room.dao.UserDao

/**
 * Created by Purwa Shadr Al 'urwa on 15/05/2021
 */

@Database(
    entities = [
        AnimeEntity::class,
        AnimeDetailEntity::class,
        MangaEntity::class,
        MangaRemoteKeysEntity::class,
        UserEntity::class,
        AnimeSuggestionKeyEntity::class,
        AnimeRankingKeyEntity::class,
    ],
    version = 30,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AnimeRisutoDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    abstract fun mangaDao(): MangaDao

    abstract fun mangaRemoteKeysDao(): MangaRemoteKeysDao

    abstract fun animeDetailDao(): AnimeDetailDao

    abstract fun userDao(): UserDao

    abstract fun animeRemoteKeysDao(): AnimeSuggestionKeyDao

    abstract fun animeRankingKeyDao(): AnimeRankingKeyDao
}
