package com.alurwa.animerisuto.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alurwa.animerisuto.data.source.local.entity.MangaRemoteKeysEntity

/**
 * Created by Purwa Shadr Al 'urwa on 31/05/2021
 */

@Dao
interface MangaRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<MangaRemoteKeysEntity>)

    @Query("SELECT * FROM manga_remote_key WHERE id = :mangaId")
    suspend fun remoteKeysId(mangaId: Int): MangaRemoteKeysEntity?

    @Query("DELETE FROM manga_remote_key")
    suspend fun clearRemoteKeys()
}
