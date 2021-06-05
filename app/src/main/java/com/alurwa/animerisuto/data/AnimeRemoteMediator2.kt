package com.alurwa.animerisuto.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeRemoteKeysEntity
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.utils.DataMapper
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

/**
 * Created by Purwa Shadr Al 'urwa on 17/05/2021
 */

@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator2(
    private val query: String,
    private val apiService: ApiService,
    private val animeRisutoDatabase: AnimeRisutoDatabase
) : RemoteMediator<Int, AnimeEntity>() {

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                null
            }

            LoadType.PREPEND -> {
                Timber.d("prepend prevKey")
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                // Query DB for SubredditRemoteKey for the subreddit.
                // SubredditRemoteKey is a wrapper object we use to keep track of page keys we
                // receive from the Reddit API to fetch the next or previous page.
                val remoteKey = animeRisutoDatabase.withTransaction {
                    // animeRisutoDatabase.animeRemoteKeysDao().remoteKeysAnimeName("Anime")

                    animeRisutoDatabase.animeRemoteKeysDao().remoteKeysAnime()
                }

                // We must explicitly check if the page key is null when appending, since the
                // Reddit API informs the end of the list by returning null for page key, but
                // passing a null key to Reddit API will fetch the initial page.
                if (remoteKey?.nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                Timber.d("append nextKey")

                remoteKey.nextKey
            }
        }

        try {
            // val offset = (page ?: 0) * state.config.pageSize
            val offset = page ?: MYANIMELIST_STARTING_INDEX

            Timber.d("offset" + offset.toString())
            Timber.d("page" + page.toString())

            val limit = when (loadType) {
                LoadType.REFRESH -> state.config.initialLoadSize
                else -> state.config.pageSize
            }
            val apiResponse = apiService.getAnimeSuggestion(
                offset,
                limit
            )

            val animeList = apiResponse.data
            val endOfPaginationReached = animeList.isEmpty()
            animeRisutoDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    animeRisutoDatabase.animeRemoteKeysDao().clearAnimeRemoteKeys()
                    animeRisutoDatabase.animeDao().clearAnimeList()
                }
                //   val prevKey = if (page == MYANIMELIST_STARTING_INDEX) null else page - 1
                // val nextKey = if (endOfPaginationReached) null else page?.plus(1)
                //  val nextKey = Uri.parse(apiResponse.paging.next).getQueryParameter("offset")?.toInt()
                val nextKey = if (endOfPaginationReached) null else offset + limit
                val keys = animeList.map {
                    AnimeRemoteKeysEntity(
                        // id = it.node.id,
                        nextKey = nextKey
                    )
                }

                //  Timber.d("prefKey" + prevKey.toString())
                Timber.d("nextKey" + nextKey.toString())

                val animeListEntity =
                    DataMapper.animeResponseListToEntityWithPaging(animeList, offset)

                animeRisutoDatabase.animeRemoteKeysDao().insertAll(keys)
                animeRisutoDatabase.animeDao().insertAll(animeListEntity)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    companion object {
        const val MYANIMELIST_STARTING_INDEX = 0
    }
}
