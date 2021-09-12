package com.alurwa.animerisuto.data.source.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import com.alurwa.animerisuto.data.source.local.entity.UserAnimeListEntity
import com.alurwa.animerisuto.data.source.local.resultentity.UserAnimeListKeyAndDB

@Dao
abstract class UserAnimeListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = AnimeDetailEntity::class)
    abstract suspend fun insert(entity: UserAnimeListEntity): Long

    @Update(entity = AnimeDetailEntity::class)
    abstract suspend fun update(entity: UserAnimeListEntity)

    @Transaction
    @Query("SELECT * FROM user_anime_list_key ORDER BY id ASC")
    abstract fun getAnimeListStatusKeyAndAnime(): PagingSource<Int, UserAnimeListKeyAndDB>

    open suspend fun insertOrUpdate(entity: UserAnimeListEntity) {
        if (insert(entity) == -1L) {
            update(entity)
        }
    }

    @Transaction
    open suspend fun insertOrUpdate(entities: List<UserAnimeListEntity>) {
        entities.forEach {
            insertOrUpdate(it)
        }
    }
}