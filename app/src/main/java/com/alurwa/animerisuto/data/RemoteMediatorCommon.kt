package com.alurwa.animerisuto.data

import com.alurwa.animerisuto.data.source.local.entity.MangaEntity
import com.alurwa.animerisuto.data.source.local.entity.MangaRemoteKeysEntity
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.data.source.remote.response.MangaListResponse
import com.alurwa.animerisuto.utils.DataMapper

/**
 * Created by Purwa Shadr Al 'urwa on 28/06/2021
 */

class RemoteMediatorCommon(
    private val animeRisutoDatabase: AnimeRisutoDatabase,
    private val apiService: ApiService
) : AbstractRemoteMediator<MangaEntity, MangaListResponse, MangaRemoteKeysEntity>() {

    override fun getDatabase(): AnimeRisutoDatabase = animeRisutoDatabase

    override suspend fun doDatabaseInRefresh() {
        animeRisutoDatabase.mangaRemoteKeysDao().clearRemoteKeys()
        animeRisutoDatabase.mangaDao().clearMangaList()
    }

    override suspend fun getDataList(offset: Int, pageSize: Int): List<MangaListResponse> =
        apiService.getMangaRanking(
            "all",
            offset,
            pageSize
        ).data

    override suspend fun insertToRemoteKeys(
        listResponse: List<MangaListResponse>,
        prevKey: Int?,
        nextKey: Int?
    ) {
        val keys = listResponse.map {
            MangaRemoteKeysEntity(id = it.node.id, prevKey = prevKey, nextKey = nextKey)
        }
        animeRisutoDatabase.mangaRemoteKeysDao().insertAll(keys)
    }

    override suspend fun insertToEntity(listResponse: List<MangaListResponse>, offset: Int) {
        val mangaListEntity = DataMapper.mangaResponseListToEntityWithPaging(listResponse, offset)

        animeRisutoDatabase.mangaDao().insertAll(mangaListEntity)
    }

    override suspend fun getRemoteKeyId(resultEntity: MangaEntity): MangaRemoteKeysEntity? =
        animeRisutoDatabase.mangaRemoteKeysDao().remoteKeysId(resultEntity.id)

    override fun getRemoteKeyNext(remoteKey: MangaRemoteKeysEntity?): Int? = remoteKey?.nextKey
    override fun getRemoteKeyPrev(remoteKey: MangaRemoteKeysEntity?): Int? = remoteKey?.prevKey
}
