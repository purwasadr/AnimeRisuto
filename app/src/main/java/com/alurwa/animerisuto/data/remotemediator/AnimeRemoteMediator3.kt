package com.alurwa.animerisuto.data.remotemediator

import com.alurwa.animerisuto.data.source.local.entity.AnimeRemoteKeysEntity
import com.alurwa.animerisuto.data.source.local.resultentity.SuggestionKeysAndAnimeEntity
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.data.source.remote.response.AnimeListResponse
import com.alurwa.animerisuto.utils.DataMapper

/**
 * Created by Purwa Shadr Al 'urwa on 18/07/2021
 */

class AnimeRemoteMediator3(
    private val apiService: ApiService,
    private val database: AnimeRisutoDatabase,
) : AbstractRemoteMediator<SuggestionKeysAndAnimeEntity, AnimeListResponse, AnimeRemoteKeysEntity>() {
    override fun getDatabase(): AnimeRisutoDatabase = database

    override suspend fun doDatabaseInRefresh() {
        database.animeRemoteKeysDao().clearRemoteKeys()
        // database.animeDao().clearAnimeList()
    }

    override suspend fun getDataList(offset: Int, pageSize: Int): List<AnimeListResponse> {
        return apiService.getAnimeSuggestion(
            offset,
            pageSize
        ).data
    }

    override suspend fun getRemoteKeyId(resultEntity: SuggestionKeysAndAnimeEntity): AnimeRemoteKeysEntity? =
        database.animeRemoteKeysDao().remoteKeysId(resultEntity.animeEntity.id)

    override fun getRemoteKeyNext(remoteKey: AnimeRemoteKeysEntity?): Int? = remoteKey?.nextKey

    override fun getRemoteKeyPrev(remoteKey: AnimeRemoteKeysEntity?): Int? = remoteKey?.prevKey

    override suspend fun getRemoteKeyToClosest(state: SuggestionKeysAndAnimeEntity?): AnimeRemoteKeysEntity? {
        return state?.animeEntity?.id?.let { id ->
            database.animeRemoteKeysDao().remoteKeysId(id)
        }
    }

    override suspend fun insertToDatabase(
        listResponse: List<AnimeListResponse>,
        offset: Int,
        prevKey: Int?,
        nextKey: Int?
    ) {
        val keys = listResponse.mapIndexed { index, it ->
            AnimeRemoteKeysEntity(
                id = it.node.id,
                no = index + offset,
                prevKey = prevKey,
                nextKey = nextKey
            )
        }

        val animeListEntity =
            DataMapper.animeResponseListToEntityWithPaging(
                listResponse,
                offset
            )
        database.animeRemoteKeysDao().insertAll(keys)
        database.animeDao().insertAll(animeListEntity)
    }
}
