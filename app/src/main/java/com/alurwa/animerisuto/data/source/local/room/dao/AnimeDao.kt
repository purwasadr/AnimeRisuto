package com.alurwa.animerisuto.data.source.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity
import com.alurwa.animerisuto.data.source.local.resultentity.SuggestionKeysAndAnimeEntity

/**
 * Created by Purwa Shadr Al 'urwa on 15/05/2021
 */

@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animeList: List<AnimeEntity>)

    @Query("SELECT * FROM anime_list ORDER BY `no` ASC")
    fun getAnimeList(): PagingSource<Int, AnimeEntity>

    @Transaction
    @Query("SELECT * FROM anime_suggestion_remote_key ORDER BY `no` ASC")
    fun getRemoteKeyAndAnime(): PagingSource<Int, SuggestionKeysAndAnimeEntity>

    @Query("DELETE FROM anime_list")
    suspend fun clearAnimeList()
}
