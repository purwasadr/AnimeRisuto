package com.alurwa.animerisuto.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Purwa Shadr Al 'urwa on 20/06/2021
 */

@Dao
interface AnimeDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(animeDetailList: AnimeDetailEntity)

    @Query("SELECT * FROM anime_detail WHERE id= :animeId")
    fun getAnimeDetail(animeId: Int): Flow<AnimeDetailEntity?>

    @Query("DELETE FROM anime_detail")
    suspend fun clearAnimeDetail()
}
