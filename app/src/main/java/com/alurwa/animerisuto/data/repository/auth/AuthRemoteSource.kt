package com.alurwa.animerisuto.data.repository.auth

import com.alurwa.animerisuto.BuildConfig
import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.data.source.remote.network.LoginService
import com.alurwa.animerisuto.data.source.remote.response.AccessTokenResponse
import com.alurwa.animerisuto.di.DispatchersIO
import com.alurwa.animerisuto.utils.flowHandleRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRemoteSource @Inject constructor(
    private val service: LoginService,
    @DispatchersIO
    private val dispatcher: CoroutineDispatcher,
) {
    fun getAccessToken(
        code: String,
        codeVerifier: String
    ): Flow<ApiResponse<AccessTokenResponse>> =

        flowHandleRequest(dispatcher) {
            service.getAccessToken(BuildConfig.CLIENT_ID, code, codeVerifier)
        }
}