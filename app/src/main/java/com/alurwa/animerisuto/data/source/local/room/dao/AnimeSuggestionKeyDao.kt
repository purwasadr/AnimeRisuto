package com.alurwa.animerisuto.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alurwa.animerisuto.data.source.local.entity.AnimeSuggestionKeyEntity

/**
 * Created by Purwa Shadr Al 'urwa on 18/07/2021
 */

@Dao
interface AnimeSuggestionKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<AnimeSuggestionKeyEntity>)

    @Query("SELECT * FROM ${AnimeSuggestionKeyEntity.TABLE_NAME} WHERE id = :id")
    suspend fun remoteKeysId(id: Int): AnimeSuggestionKeyEntity?

    @Query("DELETE FROM ${AnimeSuggestionKeyEntity.TABLE_NAME}")
    suspend fun clearRemoteKeys()
}
