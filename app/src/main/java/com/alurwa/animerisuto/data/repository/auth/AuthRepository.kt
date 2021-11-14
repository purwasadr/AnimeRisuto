package com.alurwa.animerisuto.data.repository.auth

import com.alurwa.animerisuto.data.Result
import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteSource: AuthRemoteSource,
    private val localSource: AuthLocalSource
) {
    fun getAccessToken(
        code: String,
        codeVerifier: String
    ) = flow<Result<Boolean>> {
        emit(Result.Loading)
        val response = remoteSource.getAccessToken(code, codeVerifier)
        when (val apiResponse = response.first()) {
            is ApiResponse.Success -> {
                val responseData = apiResponse.data

                localSource.saveAuth(
                    responseData.accessToken,
                    responseData.refreshToken,
                    true
                )

                emit(Result.Success(true))
            }

            is ApiResponse.Error -> {
                emit(Result.errorMessage(apiResponse.errorMessage))
            }
        }
    }

    fun isLogged() = localSource.isLogged()

    fun logout() {
        localSource.logOut()
    }
}