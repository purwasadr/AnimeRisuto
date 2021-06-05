package com.alurwa.animerisuto.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Purwa Shadr Al 'urwa on 10/05/2021
 */

class TokenInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = SessionManager(context).getAccessToken()

        return chain.proceed(
            chain.request().newBuilder().also {
                if (accessToken != null) {
                    it.addHeader("Authorization", "Bearer $accessToken")
                }
            }.build()
        )
    }
}
