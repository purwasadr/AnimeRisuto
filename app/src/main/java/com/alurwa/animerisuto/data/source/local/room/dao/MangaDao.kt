package com.alurwa.animerisuto.data.source.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alurwa.animerisuto.data.source.local.entity.MangaEntity

/**
 * Created by Purwa Shadr Al 'urwa on 31/05/2021
 */

@Dao
interface MangaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mangaList: List<MangaEntity>)

    @Query("SELECT * FROM manga_list ORDER BY `no` ASC")
    fun getMangaList(): PagingSource<Int, MangaEntity>

    @Query("DELETE FROM manga_list")
    suspend fun clearMangaList()
}
