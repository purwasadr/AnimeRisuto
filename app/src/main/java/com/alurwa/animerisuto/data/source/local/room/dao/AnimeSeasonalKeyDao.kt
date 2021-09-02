package com.alurwa.animerisuto.data.source.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.alurwa.animerisuto.data.source.local.entity.AnimeSeasonalKeyEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeSeasonalKeyEntity.Companion.TABLE_NAME
import com.alurwa.animerisuto.data.source.local.resultentity.SeasonalKeyAndAnimeEntity

@Dao
abstract class AnimeSeasonalKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(remoteKey: List<AnimeSeasonalKeyEntity>)

    // `_rowid_`
    @Transaction
    @Query("SELECT * FROM $TABLE_NAME WHERE type = :type AND year = :year ORDER BY id ASC")
    abstract fun getSeasonalKeyAndAnime(type: String, year: Int): PagingSource<Int, SeasonalKeyAndAnimeEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE anime_id = :animeId AND type = :type AND year = :year")
    abstract suspend fun remoteKeyId(type: String, year: Int, animeId: Int): AnimeSeasonalKeyEntity?

    @Query("DELETE FROM $TABLE_NAME WHERE type = :type AND year = :year")
    abstract suspend fun clearRemoteKeys(type: String, year: Int)
}