package com.alurwa.animerisuto.data.remotemediator

import com.alurwa.animerisuto.data.source.local.entity.AnimeSuggestionKeyEntity
import com.alurwa.animerisuto.data.source.local.resultentity.SuggestionKeysAndAnimeEntity
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.data.source.remote.response.AnimeListResponse
import com.alurwa.animerisuto.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Purwa Shadr Al 'urwa on 18/07/2021
 */

class AnimeSuggestionRemoteMediator(
    private val apiService: ApiService,
    private val database: AnimeRisutoDatabase,
) : AbstractRemoteMediator<SuggestionKeysAndAnimeEntity, AnimeListResponse, AnimeSuggestionKeyEntity>() {
    override fun getDatabase(): AnimeRisutoDatabase = database

    override suspend fun doDatabaseInRefresh() {
        database.animeSuggestionKeyDao().clearRemoteKeys()
        // database.animeDao().clearAnimeList()
    }

    override suspend fun getDataList(offset: Int, pageSize: Int): List<AnimeListResponse> {
        return withContext(Dispatchers.IO){
            apiService.getAnimeSuggestion(
                offset,
                pageSize
            ).data
        }
    }

    override suspend fun getRemoteKeyId(resultEntity: SuggestionKeysAndAnimeEntity): AnimeSuggestionKeyEntity? =
        database.animeSuggestionKeyDao().remoteKeysId(resultEntity.animeEntity.id)

    override fun getRemoteKeyNext(remoteKey: AnimeSuggestionKeyEntity?): Int? = remoteKey?.nextKey

    override fun getRemoteKeyPrev(remoteKey: AnimeSuggestionKeyEntity?): Int? = remoteKey?.prevKey

    override suspend fun getRemoteKeyToClosest(state: SuggestionKeysAndAnimeEntity?): AnimeSuggestionKeyEntity? {
        return state?.animeEntity?.id?.let { id ->
            database.animeSuggestionKeyDao().remoteKeysId(id)
        }
    }

    override suspend fun insertToDatabase(
        listResponse: List<AnimeListResponse>,
        offset: Int,
        prevKey: Int?,
        nextKey: Int?
    ) {
        val keys = listResponse.mapIndexed { index, it ->
            AnimeSuggestionKeyEntity(
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
        database.animeDao().insertOrUpdate(animeListEntity)
        database.animeSuggestionKeyDao().insertAll(keys)
    }
}
