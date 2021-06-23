package com.alurwa.animerisuto.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeRemoteKeysEntity
import com.alurwa.animerisuto.data.source.local.entity.MangaEntity
import com.alurwa.animerisuto.data.source.local.entity.MangaRemoteKeysEntity

/**
 * Created by Purwa Shadr Al 'urwa on 15/05/2021
 */

@Database(
    entities = [
        AnimeEntity::class,
        AnimeRemoteKeysEntity::class,
        AnimeDetailEntity::class,
        MangaEntity::class,
        MangaRemoteKeysEntity::class
    ],
    version = 8,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AnimeRisutoDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    abstract fun animeRemoteKeysDao(): AnimeRemoteKeysDao

    abstract fun mangaDao(): MangaDao

    abstract fun mangaRemoteKeysDao(): MangaRemoteKeysDao

    abstract fun animeDetailDao(): AnimeDetailDao
}
