package com.alurwa.animerisuto.data.source.remote.network

import android.content.Context
import androidx.annotation.IntRange
import com.alurwa.animerisuto.BuildConfig
import com.alurwa.animerisuto.data.source.remote.response.AccessTokenResponse
import com.alurwa.animerisuto.data.source.remote.response.AnimeDetailResponse
import com.alurwa.animerisuto.data.source.remote.response.AnimeRankingResponse
import com.alurwa.animerisuto.data.source.remote.response.AnimeSuggestionsResponse
import com.alurwa.animerisuto.data.source.remote.response.MangaRankingResponse
import com.alurwa.animerisuto.data.source.remote.response.UserAnimeListResponse
import com.alurwa.animerisuto.data.source.remote.response.UserResponse
import com.alurwa.animerisuto.utils.TokenAuthenticator
import com.alurwa.animerisuto.utils.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Created by Purwa Shadr Al 'urwa on 04/05/2021
 */

interface ApiService {

    @GET("v2/anime?q=one&limit=4")
    suspend fun getAnimeList(): AccessTokenResponse

    @GET("v2/anime?fields=$ANIME_LIST_FIELD")
    suspend fun getAnimeSearch(
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): AnimeSuggestionsResponse

    @GET("v2/anime/suggestions?fields=synopsis,rank,mean,genres")
    suspend fun getAnimeSuggestion(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): AnimeSuggestionsResponse

    @GET("v2/anime/{id}")
    suspend fun getAnimeDetails(
        @Path("id") id: Int,
        @Query("fields") fields: String = ANIME_DETAILS_FIELD,
    ): AnimeDetailResponse

    @GET("v2/anime/ranking?")
    suspend fun getAnimeRanking(
        @Query("ranking_type") rankingType: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("fields") fields: String = ANIME_LIST_FIELD,
    ): AnimeRankingResponse

    @GET("v2/anime/season/{year}/{season}")
    suspend fun getAnimeSeasonal(
        @Path("season") season: String,
        @Path("year") year: Int,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("fields") fields: String = ANIME_LIST_FIELD,
    ): AnimeSuggestionsResponse

    @GET("v2/manga/ranking?fields=synopsis,rank,mean,genres")
    suspend fun getMangaRanking(
        @Query("ranking_type") rankingType: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): MangaRankingResponse

    @GET("v2/users/@me")
    suspend fun getUser(
        @Query("fields") fields: String = USER_FIELD
    ): UserResponse

    @FormUrlEncoded
    @PATCH("v2/anime/{anime_id}/my_list_status")
    suspend fun updateUserAnimeList(
        @Path("anime_id") animeId: Int,
        @Field("status") status: String,
        @Field("is_rewatching") isRewatching: Boolean? = null,
        @IntRange(from = 0, to = 10) @Field("score") score: Int,
        @Field("num_watched_episodes") numWatchedEpisodes: Int,
        @IntRange(from = 0, to = 2) @Field("priority") priority: Int? = null,
        @Field("num_times_rewatched") numTimesRewatched : Int? = null,
        @Field("rewatch_value") rewatchValue: Int? = null,
        @Field("tags")  tags: String? = null,
        @Field("comments") comments: String? = null
    ): UserAnimeListResponse

    @GET("v2/users/@me/animelist")
    suspend fun getUserAnimeList(
        @Query("status") status: String? = null,
        @Query("sort") sort: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): UserResponse

    companion object {
        private const val API_BASE_URL = "https://api.myanimelist.net/"
        private const val ANIME_LIST_FIELD = "synopsis,rank,mean,genres"
        private const val ANIME_DETAILS_FIELD =
            "id,title,main_picture,alternative_titles,start_date," +
                    "end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw," +
                    "created_at,updated_at,media_type,status,genres,my_list_status,num_episodes," +
                    "start_season,broadcast,source,average_episode_duration,rating,pictures,background," +
                    "related_anime,related_manga,recommendations,studios,statistics"

        private const val USER_FIELD = "anime_statistics,gender,is_supporter,picture"

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
