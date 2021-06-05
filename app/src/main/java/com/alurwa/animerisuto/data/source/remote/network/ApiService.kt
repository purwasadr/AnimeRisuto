package com.alurwa.animerisuto.data.source.remote.network

import android.content.Context
import com.alurwa.animerisuto.BuildConfig
import com.alurwa.animerisuto.data.source.remote.response.AccesTokenResponse
import com.alurwa.animerisuto.data.source.remote.response.AnimeSuggestions
import com.alurwa.animerisuto.data.source.remote.response.MangaRankingResponse
import com.alurwa.animerisuto.data.source.remote.response.UserResponse
import com.alurwa.animerisuto.utils.TokenAuthenticator
import com.alurwa.animerisuto.utils.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Created by Purwa Shadr Al 'urwa on 04/05/2021
 */

interface ApiService {

    @GET("v2/anime?q=one&limit=4")
    suspend fun getAnimeList(): AccesTokenResponse

    @GET("v2/anime/suggestions?fields=synopsis,rank,mean,genres")
    suspend fun getAnimeSuggestion(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): AnimeSuggestions

    @GET("v2/manga/ranking?fields=synopsis,rank,mean,genres")
    suspend fun getMangaRanking(
        @Query("ranking_type") rankingType: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): MangaRankingResponse

    @GET("v2/users/@me")
    suspend fun getUser(
        @Query("fields") fields: String
    ): UserResponse

    companion object {
        private const val API_BASE_URL = "https://api.myanimelist.net/"

        fun create(context: Context, loginService: LoginService): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor(context))
                .also {
                    if (BuildConfig.DEBUG) {
                        it.addInterceptor(
                            HttpLoggingInterceptor().setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                        )
                    }
                }
                .authenticator(TokenAuthenticator(context, loginService))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
    }
}
