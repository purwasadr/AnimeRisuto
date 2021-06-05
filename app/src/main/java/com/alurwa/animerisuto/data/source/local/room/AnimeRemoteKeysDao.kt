package com.alurwa.animerisuto.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alurwa.animerisuto.data.source.local.entity.AnimeRemoteKeysEntity

/**
 * Created by Purwa Shadr Al 'urwa on 16/05/2021
 */

@Dao
interface AnimeRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<AnimeRemoteKeysEntity>)

    @Query("SELECT * FROM anime_remote_keys WHERE name = :name")
    suspend fun remoteKeysAnimeName(name: String): AnimeRemoteKeysEntity?

    @Query("SELECT * FROM anime_remote_keys ORDER BY next_key DESC")
    suspend fun remoteKeysAnime(): AnimeRemoteKeysEntity?

    @Query("DELETE FROM anime_remote_keys")
    suspend fun clearAnimeRemoteKeys()
}
