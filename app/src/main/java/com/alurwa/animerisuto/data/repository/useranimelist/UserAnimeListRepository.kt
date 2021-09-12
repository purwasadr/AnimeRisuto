package com.alurwa.animerisuto.data.repository.useranimelist

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.alurwa.animerisuto.data.Resource
import com.alurwa.animerisuto.data.mapper.UserAnimeListResponseToDB
import com.alurwa.animerisuto.data.remotemediator.UserAnimeListRemoteMediator
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.data.source.remote.response.AnimeListStatusResponse
import com.alurwa.animerisuto.model.UserAnimeList
import com.alurwa.animerisuto.model.UserAnimeListParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserAnimeListRepository @Inject constructor(
    private val remoteSource: UserAnimeListRemoteSource,
    private val service: ApiService,
    private val database: AnimeRisutoDatabase,
    private val userAnimeListMapper: UserAnimeListResponseToDB
) {
    fun updateUserAnimeList(userAnimeList: UserAnimeListParam) =
        flow<Resource<AnimeListStatusResponse>> {
            emit(Resource.Loading())

            when (val apiResponse = remoteSource.updateUserAnimeList(userAnimeList).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(apiResponse.data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        }

    fun getUserAnimeListPaging(): Flow<PagingData<UserAnimeList>> {
        val pagingSourceFactory = {
            database.userAnimeListDao().getAnimeListStatusKeyAndAnime()
        }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = UserAnimeListRemoteMediator(
                null,
                "anime_title",
                service,
                database,
                userAnimeListMapper
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { result ->
                UserAnimeList(
                    id = result.relation.id,
                    title = result.relation.title,
                    posterPath = result.relation.mainPictureUrl.orEmpty(),
                    num_episode_watched = result.relation.num_episode_watched ?: 0,
                    total_episode = result.relation.numEpisodes,
                    score = result.relation.list_status_score ?: 0,
                    status = result.relation.list_status_status
                )
            }
        }
    }
}