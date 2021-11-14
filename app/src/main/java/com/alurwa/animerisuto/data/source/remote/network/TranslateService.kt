package com.alurwa.animerisuto.data.source.remote.network

import com.alurwa.animerisuto.BuildConfig
import com.alurwa.animerisuto.data.source.remote.response.TranslateResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface TranslateService {
    @GET("translate")
    suspend fun getTranslate(
        @Query("engine") engine: String = "google",
        @Query("text") text: String,
        @Query("to") to: String = "id"
    ): TranslateResponse

    companion object {
        fun create(): TranslateService {
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
                .baseUrl("https://amm-api-translate.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(TranslateService::class.java)
        }
    }
}