package com.alurwa.animerisuto.data.repository.user

import com.alurwa.animerisuto.data.NetworkBoundResource
import com.alurwa.animerisuto.data.Resource
import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.data.source.remote.response.UserResponse
import com.alurwa.animerisuto.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
   private val localSource: UserLocalSource,
   private val remoteSource: UserRemoteSource
) {
    fun getUser(): Flow<Resource<User?>> =
        object : NetworkBoundResource<User?, UserResponse>() {
            override fun loadFromDB(): Flow<User?> =
                localSource.getUser().map {
                    it?.toUserDomain
                }

            override fun shouldFetch(): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> =
                remoteSource.getUser()

            override suspend fun saveCallResult(data: UserResponse) {
                localSource.insertUser(data.toUserEntity)
            }
        }.asFlow()
}