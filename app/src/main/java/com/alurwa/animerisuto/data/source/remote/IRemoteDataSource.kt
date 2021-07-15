package com.alurwa.animerisuto.data.source.remote

import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.data.source.remote.response.AccessTokenResponse
import com.alurwa.animerisuto.data.source.remote.response.AnimeDetailResponse
import com.alurwa.animerisuto.data.source.remote.response.UserResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by Purwa Shadr Al 'urwa on 23/06/2021
 */

interface IRemoteDataSource {
    suspend fun getAccesToken(
        code: String,
        codeVerifier: String
    ): Flow<ApiResponse<AccessTokenResponse>>

    suspend fun getAnimeDetails(id: Int): Flow<ApiResponse<AnimeDetailResponse>>

    suspend fun getUser(): Flow<ApiResponse<UserResponse>>
}
