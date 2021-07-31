package com.alurwa.animerisuto.data.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.alurwa.animerisuto.data.source.local.entity.MangaEntity
import com.alurwa.animerisuto.data.source.local.entity.MangaRemoteKeysEntity
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.utils.DataMapper
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

/**
 * Created by Purwa Shadr Al 'urwa on 12/05/2021
 */

@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator(
    private val apiService: ApiService,
    private val animeRisutoDatabase: AnimeRisutoDatabase
) : RemoteMediator<Int, MangaEntity>() {

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MangaEntity>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: MYANIMELIST_STARTING_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            val offset = page * state.config.pageSize
            Timber.d("offset%s", offset.toString())
            Timber.d("page%s", page.toString())
            val apiResponse = apiService.getMangaRanking(
                "all",
                offset,
                state.config.pageSize
            )

            val mangaList = apiResponse.data
            val endOfPaginationReached = mangaList.isEmpty()
            animeRisutoDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    animeRisutoDatabase.mangaRemoteKeysDao().clearRemoteKeys()
                    animeRisutoDatabase.mangaDao().clearMangaList()
                }
                val prevKey = if (page == MYANIMELIST_STARTING_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = mangaList.map {
                    MangaRemoteKeysEntity(id = it.node.id, prevKey = prevKey, nextKey = nextKey)
                }

                Timber.d("prefKey" + prevKey.toString())
                Timber.d("nextKey" + nextKey.toString())

                //  val animeListEntity = DataMapper.animeResponseListToEntity(animeList)
                val mangaListEntity = DataMapper.mangaResponseListToEntityWithPaging(mangaList, offset)
                animeRisutoDatabase.mangaRemoteKeysDao().insertAll(keys)
                animeRisutoDatabase.mangaDao().insertAll(mangaListEntity)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MangaEntity>
    ): MangaRemoteKeysEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { manga ->
                // Get the remote keys of the last item retrieved
                animeRisutoDatabase.mangaRemoteKeysDao().remoteKeysId(manga.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MangaEntity>
    ): MangaRemoteKeysEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { manga ->
                // Get the remote keys of the first items retrieved
                animeRisutoDatabase.mangaRemoteKeysDao().remoteKeysId(manga.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MangaEntity>
    ): MangaRemoteKeysEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { mangaId ->
                animeRisutoDatabase.mangaRemoteKeysDao().remoteKeysId(mangaId)
            }
        }
    }

    companion object {
        const val MYANIMELIST_STARTING_INDEX = 0
    }
}
