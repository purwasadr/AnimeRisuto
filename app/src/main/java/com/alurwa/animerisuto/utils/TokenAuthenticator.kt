package com.alurwa.animerisuto.utils

import android.content.Context
import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.data.source.remote.network.LoginService
import com.alurwa.animerisuto.data.source.remote.response.AccesTokenResponse
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Created by Purwa Shadr Al 'urwa on 10/05/2021
 */

class TokenAuthenticator(
    private val context: Context,
    private val loginService: LoginService
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val sessionManager = SessionManager(context)
            sessionManager.getRefreshToken()?.let {
                when (val responseToken = getRefreshToken(it)) {
                    is ApiResponse.Success -> {
                        sessionManager.refreshToken(
                            responseToken.data.accessToken,
                            responseToken.data.refreshToken
                        )

                        response.request.newBuilder()
                            .header(
                                "Authorization",
                                "Bearer ${responseToken.data.accessToken}"
                            )
                            .build()
                    }

                    else -> null
                }
            }
        }
    }

    private suspend fun getRefreshToken(
        refreshToken: String
    ): ApiResponse<AccesTokenResponse> =
        try {
            val response = loginService.getRefreshToken(refreshToken)
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e.toString())
        }
}
