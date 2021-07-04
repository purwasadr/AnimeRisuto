package com.alurwa.animerisuto.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.model.Anime
import com.alurwa.animerisuto.utils.DataMapper
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

/**
 * Created by Purwa Shadr Al 'urwa on 28/06/2021
 */

class AnimePagingSource(
    private val apiService: ApiService,
    private val query: String,
) : PagingSource<Int, Anime>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Anime> {
        // val position = params.key ?: STARTING_PAGING_INDEX
        return try {
            val offset = params.key ?: STARTING_PAGING_INDEX
            val loadSize = params.loadSize

            Timber.d("Offset : $offset")

            val response = apiService.getAnimeSearch(query, offset, loadSize)
            val repos = DataMapper.animeListResponseToDomain(response.data)

            val nextKey = if (response.paging.next == null) {
                null
            } else {
                offset + loadSize
            }

            Timber.d("Next Key : $nextKey")
            LoadResult.Page(
                data = repos,
                prevKey = if (offset == STARTING_PAGING_INDEX) null else offset - loadSize,
                nextKey = nextKey
            )
        } catch (ex: IOException) {
            Timber.d(ex)
            return LoadResult.Error(ex)
        } catch (ex: HttpException) {
            Timber.d(ex)
            return LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Anime>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGING_INDEX = 0
    }
}
