package com.alurwa.animerisuto.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alurwa.animerisuto.data.source.local.entity.UserAnimeListKeyEntity

@Dao
abstract class UserAnimeListKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(remoteKeys: List<UserAnimeListKeyEntity>)


    @Query("SELECT * FROM user_anime_list_key WHERE id = :id")
    abstract suspend fun remoteKeysId(id: Int): UserAnimeListKeyEntity?

    @Query("DELETE FROM user_anime_list_key")
    abstract suspend fun clearRemoteKeys()
}