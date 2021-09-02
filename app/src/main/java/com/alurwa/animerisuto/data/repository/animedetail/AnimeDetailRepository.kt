package com.alurwa.animerisuto.data.repository.animedetail

import com.alurwa.animerisuto.data.NetworkBoundResource
import com.alurwa.animerisuto.data.Resource
import com.alurwa.animerisuto.data.mapper.AnimeDetailDBToDomain
import com.alurwa.animerisuto.data.mapper.AnimeDetailResponseToDB
import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.data.source.remote.response.AnimeDetailResponse
import com.alurwa.animerisuto.model.AnimeDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnimeDetailRepository @Inject constructor(
    private val localSource: AnimeDetailLocalSource,
    private val remoteSource: AnimeDetailRemoteSource,
    private val localSourceMapper: AnimeDetailDBToDomain,
    private val remoteSourceMapper: AnimeDetailResponseToDB
) {
    fun getAnimeDetail(id: Int): Flow<Resource<AnimeDetail?>> =
        object : NetworkBoundResource<AnimeDetail?, AnimeDetailResponse>() {
            override fun loadFromDB(): Flow<AnimeDetail?> =
                localSource.getAnimeDetail(id).map {
                    it?.let { entity ->
                        localSourceMapper.map(entity)
                    }
                }

            override fun shouldFetch(): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<AnimeDetailResponse>> =
                remoteSource.getAnimeDetails(id)

            override suspend fun saveCallResult(data: AnimeDetailResponse) {
                val entity = remoteSourceMapper.map(data)
                localSource.insertAnimeDetail(entity)
            }
        }.asFlow()
}