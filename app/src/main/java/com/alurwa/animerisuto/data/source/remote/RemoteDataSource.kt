package com.alurwa.animerisuto.data.source.remote

import com.alurwa.animerisuto.BuildConfig
import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.data.source.remote.network.LoginService
import com.alurwa.animerisuto.data.source.remote.response.AccesTokenResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Purwa Shadr Al 'urwa on 04/05/2021
 */

@Singleton
class RemoteDataSource @Inject constructor(
    private val loginService: LoginService,
    val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
) : IRemoteDataSource {

    override suspend fun getAccesToken(
        code: String,
        codeVerifier: String
    ): Flow<ApiResponse<AccesTokenResponse>> = flow {
        try {
            val response = loginService.getAccessToken(
                BuildConfig.CLIENT_ID,
                code,
                codeVerifier,
                "authorization_code"
            )
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(dispatcher)

    override suspend fun getAnimeDetails(id: Int) = flow {
        try {
            val response = apiService.getAnimeDetails(id)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(dispatcher)
}
