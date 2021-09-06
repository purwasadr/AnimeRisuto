package com.alurwa.animerisuto.data.repository.useranimelist

import com.alurwa.animerisuto.data.Resource
import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.data.source.remote.response.UserAnimeListResponse
import com.alurwa.animerisuto.model.UserAnimeListParam
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserAnimeListRepository @Inject constructor(
    private val remoteSource: UserAnimeListRemoteSource
) {
    fun updateUserAnimeList(userAnimeList: UserAnimeListParam) =
        flow<Resource<UserAnimeListResponse>> {
            emit(Resource.Loading())

            when (val apiResponse = remoteSource.updateUserAnimeList(userAnimeList).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(apiResponse.data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        }
}