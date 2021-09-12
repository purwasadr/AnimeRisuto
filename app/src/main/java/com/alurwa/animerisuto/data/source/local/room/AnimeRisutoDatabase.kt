package com.alurwa.animerisuto.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeRankingKeyEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeSeasonalKeyEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeSuggestionKeyEntity
import com.alurwa.animerisuto.data.source.local.entity.MangaEntity
import com.alurwa.animerisuto.data.source.local.entity.MangaRemoteKeysEntity
import com.alurwa.animerisuto.data.source.local.entity.UserAnimeListKeyEntity
import com.alurwa.animerisuto.data.source.local.entity.UserEntity
import com.alurwa.animerisuto.data.source.local.room.dao.AnimeDao
import com.alurwa.animerisuto.data.source.local.room.dao.AnimeDetailDao
import com.alurwa.animerisuto.data.source.local.room.dao.AnimeRankingKeyDao
import com.alurwa.animerisuto.data.source.local.room.dao.AnimeSeasonalKeyDao
import com.alurwa.animerisuto.data.source.local.room.dao.AnimeSuggestionKeyDao
import com.alurwa.animerisuto.data.source.local.room.dao.MangaDao
import com.alurwa.animerisuto.data.source.local.room.dao.MangaRemoteKeysDao
import com.alurwa.animerisuto.data.source.local.room.dao.UserAnimeListDao
import com.alurwa.animerisuto.data.source.local.room.dao.UserAnimeListKeyDao
import com.alurwa.animerisuto.data.source.local.room.dao.UserDao

@Database(
    entities = [
        AnimeDetailEntity::class,
        MangaEntity::class,
        MangaRemoteKeysEntity::class,
        UserEntity::class,
        AnimeSuggestionKeyEntity::class,
        AnimeRankingKeyEntity::class,
        AnimeSeasonalKeyEntity::class,
        UserAnimeListKeyEntity::class
    ],
    version = 36,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AnimeRisutoDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    abstract fun mangaDao(): MangaDao

    abstract fun mangaRemoteKeysDao(): MangaRemoteKeysDao

    abstract fun animeDetailDao(): AnimeDetailDao

    abstract fun userDao(): UserDao

    abstract fun animeSuggestionKeyDao(): AnimeSuggestionKeyDao

    abstract fun animeRankingKeyDao(): AnimeRankingKeyDao

    abstract fun animeSeasonalKeyDao(): AnimeSeasonalKeyDao

    abstract fun userAnimeListDao(): UserAnimeListDao

    abstract fun userAnimeListKeyDao(): UserAnimeListKeyDao
}
