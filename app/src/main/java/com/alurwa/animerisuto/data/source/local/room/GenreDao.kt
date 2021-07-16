package com.alurwa.animerisuto.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.alurwa.animerisuto.data.source.local.entity.GenreEntity

/**
 * Created by Purwa Shadr Al 'urwa on 15/07/2021
 */

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animeList: Set<GenreEntity>)
}
