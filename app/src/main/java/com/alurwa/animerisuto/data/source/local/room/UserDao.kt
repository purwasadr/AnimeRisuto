package com.alurwa.animerisuto.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alurwa.animerisuto.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Purwa Shadr Al 'urwa on 13/07/2021
 */

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): Flow<UserEntity?>

    @Query("DELETE FROM user")
    suspend fun clearUser()
}