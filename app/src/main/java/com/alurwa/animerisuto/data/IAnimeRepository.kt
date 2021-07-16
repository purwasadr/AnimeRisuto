package com.alurwa.animerisuto.data

import androidx.paging.PagingData
import com.alurwa.animerisuto.model.Anime
import com.alurwa.animerisuto.model.AnimeDetail
import com.alurwa.animerisuto.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by Purwa Shadr Al 'urwa on 04/05/2021
 */

interface IAnimeRepository {
    fun getAccessToken(
        code: String,
        codeVerifier: String
    ): Flow<Resource<Boolean>>

    fun getAnimeList(): Flow<PagingData<Anime>>

    fun getMangaPaging(): Flow<PagingData<Anime>>

    fun getAnimeDetails(id: Int): Flow<Resource<AnimeDetail?>>

    fun getAnimeSearch(query: String): Flow<PagingData<Anime>>

    fun getUser(): Flow<Resource<User?>>
}
