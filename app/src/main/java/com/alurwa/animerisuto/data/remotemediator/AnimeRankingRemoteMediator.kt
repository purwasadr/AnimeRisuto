package com.alurwa.animerisuto.data.remotemediator

import com.alurwa.animerisuto.data.source.local.entity.AnimeRankingKeyEntity
import com.alurwa.animerisuto.data.source.local.resultentity.RankingKeysAndAnimeEntity
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.data.source.remote.response.AnimeListRankedResponse
import com.alurwa.animerisuto.data.source.remote.response.toEntityWithPaging
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeRankingRemoteMediator(
    private val type: String,
    private val apiService: ApiService,
    private val database: AnimeRisutoDatabase,
    private val dispatchers: CoroutineDispatcher = Dispatchers.IO
) : AbstractRemoteMediator3<
        RankingKeysAndAnimeEntity,
        AnimeListRankedResponse,
        AnimeRankingKeyEntity
        >(database) {

    override suspend fun onLoadStateRefresh() {
        withContext(dispatchers) {
            database.animeRankingKeyDao().clearRemoteKeys(type)
        }
    }

    override suspend fun insertToDatabase(
        listResponse: List<AnimeListRankedResponse>,
        offset: Int,
        prevKey: Int?,
        nextKey: Int?
    ) {
        val keys = listResponse.mapIndexed { index, it ->

            AnimeRankingKeyEntity(
                type = type,
                animeId = it.node.id,
                prevKey = prevKey,
                nextKey = nextKey
            )
        }

        withContext(dispatchers) {
            database.animeRankingKeyDao().insertAll(keys)
            database.animeDao().insertAll(listResponse.toEntityWithPaging(offset))
        }
    }

    override suspend fun getDataList(offset: Int, pageSize: Int): List<AnimeListRankedResponse> =
        withContext(dispatchers) {
            apiService.getAnimeRanking(type, offset, pageSize).data
        }

    override suspend fun getRemoteKeyId(resultEntity: RankingKeysAndAnimeEntity): AnimeRankingKeyEntity? =
        withContext(dispatchers) {
            database.animeRankingKeyDao().remoteKeyId(type, resultEntity.relation.id)
        }
}
