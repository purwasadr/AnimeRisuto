package com.alurwa.animerisuto.data.source.remote.network

import android.net.Uri
import com.alurwa.animerisuto.BuildConfig
import com.alurwa.animerisuto.constant.OAUTH_BASE_URL
import com.alurwa.animerisuto.constant.REDIRECT_URI
import com.alurwa.animerisuto.data.source.remote.response.AccessTokenResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

/**
 * Created by Purwa Shadr Al 'urwa on 10/05/2021
 */

interface LoginService {
    @FormUrlEncoded
    @POST("v1/oauth2/token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("code") code: String,
        @Field("code_verifier") codeVerifier: String,
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("redirect_uri") redirectUri: Uri = Uri.parse(REDIRECT_URI)
    ): AccessTokenResponse

    @FormUrlEncoded
    @POST("v1/oauth2/token")
    suspend fun getRefreshToken(
        @Field("refresh_token") tokenRefresh: String,
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("client_id") clientId: String = BuildConfig.CLIENT_ID,
    ): AccessTokenResponse

    companion object {

        fun create(): LoginService {
            val client = OkHttpClient.Builder()
                .also {
                    if (BuildConfig.DEBUG) {
                        it.addInterceptor(
                            HttpLoggingInterceptor().setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                        )
                    }
                }
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(OAUTH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(LoginService::class.java)
        }
    }
}
