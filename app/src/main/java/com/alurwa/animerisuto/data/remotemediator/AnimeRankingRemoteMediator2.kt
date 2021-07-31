package com.alurwa.animerisuto.data.remotemediator

import com.alurwa.animerisuto.data.source.local.entity.AnimeRankingKeyEntity
import com.alurwa.animerisuto.data.source.local.resultentity.RankingKeysAndAnimeEntity
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.data.source.remote.response.AnimeListRankedResponse
import com.alurwa.animerisuto.data.source.remote.response.toEntityWithPaging

/**
 * Created by Purwa Shadr Al 'urwa on 18/07/2021
 */

class AnimeRankingRemoteMediator2(
    private val apiService: ApiService,
    private val database: AnimeRisutoDatabase,
    private val type: String,
) : AbstractRemoteMediator<RankingKeysAndAnimeEntity, AnimeListRankedResponse, AnimeRankingKeyEntity>() {
    override fun getDatabase(): AnimeRisutoDatabase = database

    override suspend fun doDatabaseInRefresh() {
        database.animeRankingKeyDao().clearRemoteKeys(type)
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

        database.animeRankingKeyDao().insertAll(keys)
        database.animeDao().insertAll(listResponse.toEntityWithPaging(offset))
    }

    override suspend fun getDataList(offset: Int, pageSize: Int): List<AnimeListRankedResponse> =
        apiService.getAnimeRanking(type, offset, pageSize).data

    override suspend fun getRemoteKeyId(resultEntity: RankingKeysAndAnimeEntity): AnimeRankingKeyEntity? =
        database.animeRankingKeyDao().remoteKeyId(type, resultEntity.relation.id)

    override fun getRemoteKeyNext(remoteKey: AnimeRankingKeyEntity?): Int? =
        remoteKey?.nextKey

    override fun getRemoteKeyPrev(remoteKey: AnimeRankingKeyEntity?): Int? =
        remoteKey?.prevKey

    override suspend fun getRemoteKeyToClosest(state: RankingKeysAndAnimeEntity?): AnimeRankingKeyEntity? =
        state?.relation?.id?.let { animeId ->
            database.animeRankingKeyDao().remoteKeyId(type, animeId)
        }
}
