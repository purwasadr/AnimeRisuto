package com.alurwa.animerisuto.data.source.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity
import com.alurwa.animerisuto.data.source.local.resultentity.SuggestionKeysAndAnimeEntity

/**
 * Created by Purwa Shadr Al 'urwa on 15/05/2021
 */

@Dao
abstract class AnimeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = AnimeDetailEntity::class)
    abstract suspend fun insert(entity: AnimeEntity): Long

    @Transaction
    @Query("SELECT * FROM anime_suggestion_remote_key ORDER BY `no` ASC")
    abstract fun getRemoteKeyAndAnime(): PagingSource<Int, SuggestionKeysAndAnimeEntity>

    @Query("DELETE FROM anime_detail")
    abstract suspend fun clearAnimeList()

    @Update(entity = AnimeDetailEntity::class)
    abstract suspend fun update(entity: AnimeEntity)

    open suspend fun insertOrUpdate(entity: AnimeEntity) {
        if (insert(entity) == -1L) {
            update(entity)
        }
    }

    @Transaction
    open suspend fun insertOrUpdate(entities: List<AnimeEntity>) {
        entities.forEach {
            insertOrUpdate(it)
        }
    }
}
