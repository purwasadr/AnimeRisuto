package com.alurwa.animerisuto.data.repository.user

import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.data.source.remote.response.UserResponse
import com.alurwa.animerisuto.di.DispatchersIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRemoteSource @Inject constructor(
    @DispatchersIO
    private val dispatcher: CoroutineDispatcher,
    private val service: ApiService
) {

    fun getUser(): Flow<ApiResponse<UserResponse>> = flow {
        try {
            val response = service.getUser()
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(dispatcher)
}