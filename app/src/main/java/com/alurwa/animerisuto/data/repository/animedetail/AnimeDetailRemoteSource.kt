package com.alurwa.animerisuto.data.repository.animedetail

import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.di.DispatchersIO
import com.alurwa.animerisuto.utils.flowHandleRequest
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AnimeDetailRemoteSource @Inject constructor(
    private val service: ApiService,

    @DispatchersIO
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getAnimeDetails(id: Int) =
        flowHandleRequest(ioDispatcher) { service.getAnimeDetails(id) }
}