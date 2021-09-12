package com.alurwa.animerisuto.data.repository.anime

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.alurwa.animerisuto.data.AnimePagingSource
import com.alurwa.animerisuto.data.remotemediator.AnimeRankingRemoteMediator
import com.alurwa.animerisuto.data.remotemediator.AnimeSeasonalRemoteMediator
import com.alurwa.animerisuto.data.remotemediator.AnimeSuggestionRemoteMediator
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.model.Anime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val service: ApiService,
    private val database: AnimeRisutoDatabase,
) {
    fun getAnimeSuggestions(): Flow<PagingData<Anime>> {
        val pagingSourceFactory = { database.animeDao().getRemoteKeyAndAnime() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = AnimeSuggestionRemoteMediator(
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { anime ->
                anime.animeEntity.let {entity ->
                    Anime(
                        entity.id,
                        entity.title,
                        entity.posterPath,
                        entity.genres,
                        entity.mean
                    )
                }

            }
        }
    }

    fun getAnimeRankingPaging(type: String): Flow<PagingData<Anime>> {
        val pagingSourceFactory = {
            database.animeRankingKeyDao().getRankingKeyAndAnime(type)
        }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = AnimeRankingRemoteMediator(
                type,
                service,
                database,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { anime ->

                Anime(
                    anime.relation.id,
                    anime.relation.title,
                    anime.relation.posterPath,
                    anime.relation.genres,
                    anime.relation.mean
                )
            }
        }
    }

    fun getAnimeSeasonalPaging(type: String, year: Int): Flow<PagingData<Anime>> {
        val pagingSourceFactory = {
            database.animeSeasonalKeyDao().getSeasonalKeyAndAnime(type, year)
        }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = AnimeSeasonalRemoteMediator(
                type,
                year,
                database,
                service,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { anime ->
                Anime(
                    anime.relation.id,
                    anime.relation.title,
                    anime.relation.posterPath,
                    anime.relation.genres,
                    anime.relation.mean
                )
            }
        }
    }

    fun getAnimeSearch(query: String): Flow<PagingData<Anime>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { AnimePagingSource(service, query) }
        ).flow
}