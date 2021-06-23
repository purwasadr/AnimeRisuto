package com.alurwa.animerisuto.data.source.remote

import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.data.source.remote.response.AccesTokenResponse
import com.alurwa.animerisuto.data.source.remote.response.AnimeDetailResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by Purwa Shadr Al 'urwa on 23/06/2021
 */

interface IRemoteDataSource {
    suspend fun getAccesToken(
        code: String,
        codeVerifier: String
    ): Flow<ApiResponse<AccesTokenResponse>>

    suspend fun getAnimeDetails(id: Int): Flow<ApiResponse<AnimeDetailResponse>>
}
