package com.alurwa.animerisuto.data.remotemediator

import com.alurwa.animerisuto.data.mapper.UserAnimeListResponseToDB
import com.alurwa.animerisuto.data.source.local.entity.UserAnimeListKeyEntity
import com.alurwa.animerisuto.data.source.local.resultentity.UserAnimeListKeyAndDB
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.data.source.remote.response.UserAnimeListPagingResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserAnimeListRemoteMediator(

    private val status: String?,
    private val sort: String,
    private val service: ApiService,
    private val database: AnimeRisutoDatabase,
    private val userAnimeListMapper: UserAnimeListResponseToDB,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AbstractRemoteMediator3<UserAnimeListKeyAndDB, UserAnimeListPagingResponse, UserAnimeListKeyEntity>(
    database
) {
    override suspend fun onLoadStateRefresh() {
        database.userAnimeListKeyDao().clearRemoteKeys()
    }

    override suspend fun insertToDatabase(
        listResponse: List<UserAnimeListPagingResponse>,
        offset: Int,
        prevKey: Int?,
        nextKey: Int?
    ) {
        val keys = listResponse.mapIndexed { index, it ->

            UserAnimeListKeyEntity(
                relationId = it.node.id,
                prevKey = prevKey,
                nextKey = nextKey
            )
        }

        withContext(dispatcher) {
            database.userAnimeListDao().insertOrUpdate(listResponse.map {
                userAnimeListMapper.map(it)
            })
            database.userAnimeListKeyDao().insertAll(keys)
        }
    }

    override suspend fun getDataList(
        offset: Int,
        pageSize: Int
    ): List<UserAnimeListPagingResponse> =
        service.getUserAnimeList(status, sort, pageSize, offset).data

    override suspend fun getRemoteKeyId(resultEntity: UserAnimeListKeyAndDB): UserAnimeListKeyEntity? =
        database.userAnimeListKeyDao().remoteKeysId(resultEntity.relation.id)
}