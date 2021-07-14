package com.alurwa.animerisuto.data.source.local

import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import com.alurwa.animerisuto.data.source.local.entity.UserEntity
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Purwa Shadr Al 'urwa on 04/05/2021
 */

@Singleton
class LocalDataSource @Inject constructor(
    database: AnimeRisutoDatabase
) : ILocalDataSource {

    private val animeDetailDao = database.animeDetailDao()
    private val userDao = database.userDao()

    override suspend fun insertAnimeDetail(animeDetail: AnimeDetailEntity) {
        animeDetailDao.insert(animeDetail)
    }

    override fun getAnimeDetail(animeId: Int): Flow<AnimeDetailEntity?> =
        animeDetailDao.getAnimeDetail(animeId)

    override suspend fun insertUser(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

    override fun getUser(): Flow<UserEntity?> = userDao.getUser()
}
