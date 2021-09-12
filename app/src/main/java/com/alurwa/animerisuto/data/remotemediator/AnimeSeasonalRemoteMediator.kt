package com.alurwa.animerisuto.data.remotemediator

import com.alurwa.animerisuto.data.source.local.entity.AnimeSeasonalKeyEntity
import com.alurwa.animerisuto.data.source.local.resultentity.SeasonalKeyAndAnimeEntity
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.data.source.remote.response.AnimeListResponse
import com.alurwa.animerisuto.data.source.remote.response.toEntityWithPaging
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeSeasonalRemoteMediator(
    private val type: String,
    private val year: Int,
    private val database: AnimeRisutoDatabase,
    private val service: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AbstractRemoteMediator3<
        SeasonalKeyAndAnimeEntity,
        AnimeListResponse,
        AnimeSeasonalKeyEntity
        >(database) {


    override suspend fun onLoadStateRefresh() {
        database.animeSeasonalKeyDao().clearRemoteKeys(type, year)
    }

    override suspend fun insertToDatabase(
        listResponse: List<AnimeListResponse>,
        offset: Int,
        prevKey: Int?,
        nextKey: Int?
    ) {
        val keys = listResponse.mapIndexed { index, response ->
            AnimeSeasonalKeyEntity(
                type = type,
                year = year,
                animeId = response.node.id,
                prevKey = prevKey,
                nextKey = nextKey
            )
        }

        withContext(dispatcher) {
            database.animeDao().insertOrUpdate(listResponse.toEntityWithPaging(offset))
            database.animeSeasonalKeyDao().insertAll(keys)
        }
    }

    override suspend fun getDataList(offset: Int, pageSize: Int): List<AnimeListResponse> =
        withContext(dispatcher) {
            service.getAnimeSeasonal(type, year, offset, pageSize).data
        }

    override suspend fun getRemoteKeyId(resultEntity: SeasonalKeyAndAnimeEntity): AnimeSeasonalKeyEntity? =
        withContext(dispatcher) {
            database.animeSeasonalKeyDao().remoteKeyId(type, year, resultEntity.relation.id)
        }
}