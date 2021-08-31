package com.alurwa.animerisuto.data.repository.manga

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.alurwa.animerisuto.data.remotemediator.AnimeRemoteMediator
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.model.Anime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MangaRepository @Inject constructor(
    private val database: AnimeRisutoDatabase,
    private val apiService: ApiService
){

    fun getMangaPaging(): Flow<PagingData<Anime>> {
        val pagingSourceFactory = { database.mangaDao().getMangaList() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = AnimeRemoteMediator(
                apiService,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { anime ->
                Anime(anime.id, anime.title, anime.posterPath, anime.genres, anime.mean)
            }
        }
    }
}