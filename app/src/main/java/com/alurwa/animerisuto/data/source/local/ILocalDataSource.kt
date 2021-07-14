package com.alurwa.animerisuto.data.source.local

import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import com.alurwa.animerisuto.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Purwa Shadr Al 'urwa on 19/06/2021
 */

interface ILocalDataSource {
    suspend fun insertAnimeDetail(animeDetail: AnimeDetailEntity)

    fun getAnimeDetail(animeId: Int): Flow<AnimeDetailEntity?>

    suspend fun insertUser(userEntity: UserEntity)

    fun getUser(): Flow<UserEntity?>
}
