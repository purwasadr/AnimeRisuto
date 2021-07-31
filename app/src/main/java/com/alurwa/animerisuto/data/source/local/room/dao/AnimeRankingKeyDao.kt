package com.alurwa.animerisuto.data.source.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.alurwa.animerisuto.data.source.local.entity.AnimeRankingKeyEntity
import com.alurwa.animerisuto.data.source.local.resultentity.RankingKeysAndAnimeEntity

/**
 * Created by Purwa Shadr Al 'urwa on 18/07/2021
 */

@Dao
interface AnimeRankingKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<AnimeRankingKeyEntity>)

    // `_rowid_`
    @Transaction
    @Query("SELECT * FROM ${AnimeRankingKeyEntity.TABLE_NAME} WHERE type = :type ORDER BY id ASC")
    fun getRankingKeyAndAnime(type: String): PagingSource<Int, RankingKeysAndAnimeEntity>

    @Query("SELECT * FROM ${AnimeRankingKeyEntity.TABLE_NAME} WHERE anime_id = :animeId AND type = :type")
    suspend fun remoteKeyId(type: String, animeId: Int): AnimeRankingKeyEntity?

    @Query("DELETE FROM ${AnimeRankingKeyEntity.TABLE_NAME} WHERE type = :type")
    suspend fun clearRemoteKeys(type: String)
}
