package com.alurwa.animerisuto.data.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.alurwa.animerisuto.data.source.local.entity.IKeyEntity
import com.alurwa.animerisuto.data.source.local.resultentity.EntryWithRelation
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

/**
 * Kelas yang digunakan untuk mempermudah penggunaan [RemoteMediator]
 */
@OptIn(ExperimentalPagingApi::class)
abstract class AbstractRemoteMediator3<
    ResultEntity : EntryWithRelation<*, *>,
    Response,
    RemoteKeyEntity : IKeyEntity
    >(private val database: AnimeRisutoDatabase) : RemoteMediator<Int, ResultEntity>() {


    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    /**
     * Dipanggil saat loadstate dalam posisi [LoadType.REFRESH]
     */
    abstract suspend fun onLoadStateRefresh()

    /**
     * Dipanggil untuk melakukan operasi tulis ke database
     * @param listResponse daftar [Response]
     * @param offset nilai yang didapat dari halaman * pageSize
     * @param prevKey nilai key sebelumnya
     * @param nextKey nilai key selanjutnya
     */
    abstract suspend fun insertToDatabase(
        listResponse: List<Response>,
        offset: Int,
        prevKey: Int?,
        nextKey: Int?
    )

    /**
     * Dipanggil untuk mendapatkan data yang dibutuhkan
     *
     * @param offset nilai yang didapat dari halaman * pageSize
     * @param pageSize ukuran halaman
     * @return daftar object DTO
     */
    abstract suspend fun getDataList(offset: Int, pageSize: Int): List<Response>

    /**
     * Dipanggil untuk mendapatkan id dari remote key
     */
    abstract suspend fun getRemoteKeyId(resultEntity: ResultEntity): RemoteKeyEntity?

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultEntity>
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
                    onLoadStateRefresh()
                }

                insertToDatabase(mangaList, offset, prevKey, nextKey)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ResultEntity>
    ): RemoteKeyEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { manga ->
                // Get the remote keys of the last item retrieved
                // getRemoteKeysId()
                getRemoteKeyId(manga)
                //   getDatabase().mangaRemoteKeysDao().remoteKeysId(getRemoteKeyId(manga))
            }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ResultEntity>
    ): RemoteKeyEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { manga ->
                // Get the remote keys of the first items retrieved
                getRemoteKeyId(manga)
            }
    }

    /**
     * Dipanggil untuk mendapatkan key dari posisi terakhir
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ResultEntity>
    ): RemoteKeyEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { entity ->
                getRemoteKeyId(entity)
            }
        }
    }

    companion object {
        const val MYANIMELIST_STARTING_INDEX = 0
    }
}
