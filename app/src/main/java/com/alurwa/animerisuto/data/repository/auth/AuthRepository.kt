package com.alurwa.animerisuto.data.repository.auth

import com.alurwa.animerisuto.data.Resource
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
    ) = flow<Resource<Boolean>> {
        emit(Resource.Loading())
        val response = remoteSource.getAccessToken(code, codeVerifier)
        when (val apiResponse = response.first()) {
            is ApiResponse.Success -> {
                val responseData = apiResponse.data

                localSource.saveAuth(
                    responseData.accessToken,
                    responseData.refreshToken,
                    true
                )

                emit(Resource.Success(true))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    fun isLogged() = localSource.isLogged()

    fun logout() {
        localSource.logOut()
    }
}