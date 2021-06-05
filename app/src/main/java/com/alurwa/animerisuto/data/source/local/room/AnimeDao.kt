package com.alurwa.animerisuto.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity

/**
 * Created by Purwa Shadr Al 'urwa on 15/05/2021
 */

@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animeList: List<AnimeEntity>)

    @Query("SELECT * FROM anime_list ORDER BY `no` ASC")
    fun getAnimeList(): PagingSource<Int, AnimeEntity>

    @Query("DELETE FROM anime_list")
    suspend fun clearAnimeList()
}
