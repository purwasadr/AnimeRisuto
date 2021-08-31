package com.alurwa.animerisuto.data.repository.user

import com.alurwa.animerisuto.data.source.local.entity.UserEntity
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserLocalSource @Inject constructor(
    private val database: AnimeRisutoDatabase
) {

    private val userDao = database.userDao()
    fun getUser(): Flow<UserEntity?> = userDao.getUser()

    suspend fun insertUser(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

}