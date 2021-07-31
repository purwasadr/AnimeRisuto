package com.alurwa.animerisuto.data.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.alurwa.animerisuto.data.source.local.entity.IAnimeKeyEntity
import com.alurwa.animerisuto.data.source.local.entity.IEntity
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
abstract class AbstractRemoteMediator2<Response, RemoteKeyEntity : IAnimeKeyEntity>(
    private val database: AnimeRisutoDatabase
) : RemoteMediator<Int, IEntity>() {
    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    abstract suspend fun onLoadRefresh()

    abstract suspend fun insertToDatabase(
        listResponse: List<Response>,
        offset: Int,
        prevKey: Int?,
        nextKey: Int?
    )

    abstract suspend fun getDataList(offset: Int, pageSize: Int): List<Response>

    abstract suspend fun getRemoteId(resultEntity: IEntity): RemoteKeyEntity?

    abstract suspend fun getRemoteKeyToClosest(state: IEntity?): RemoteKeyEntity?

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, IEntity>
    ): MediatorResult {
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
//                val nextKey = remoteKeys?.nextKey
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

            val mangaList = getDataList(offset, state.config.pageSize)
            val endOfPaginationReached = mangaList.isEmpty()

            val prevKey = if (page == MYANIMELIST_STARTING_INDEX) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1

            Timber.d("prefKey%s", prevKey.toString())
            Timber.d("nextKey%s", nextKey.toString())

            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    onLoadRefresh()
                }

                //  val animeListEntity = DataMapper.animeResponseListToEntity(animeList)
                insertToDatabase(mangaList, offset, prevKey, nextKey)
//                insertToRemoteKeys(mangaList, prevKey, nextKey)
//                insertToEntity(mangaList, offset)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, IEntity>
    ): IAnimeKeyEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { manga ->
                // Get the remote keys of the last item retrieved
                getRemoteId(manga)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, IEntity>
    ): IAnimeKeyEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { manga ->
                // Get the remote keys of the first items retrieved
                getRemoteId(manga)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, IEntity>
    ): IAnimeKeyEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            getRemoteKeyClosestToCurrentPosition(state)
        }
    }

    companion object {
        const val MYANIMELIST_STARTING_INDEX = 0
    }
}
