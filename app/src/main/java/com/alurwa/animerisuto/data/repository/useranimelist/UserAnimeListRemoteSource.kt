package com.alurwa.animerisuto.data.repository.useranimelist

import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.di.DispatchersIO
import com.alurwa.animerisuto.model.UserAnimeListParam
import com.alurwa.animerisuto.utils.flowHandleRequest
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UserAnimeListRemoteSource @Inject constructor(
    private val service: ApiService,
    @DispatchersIO
    private val dispatcher: CoroutineDispatcher
) {

    fun updateUserAnimeList(userAnimeList: UserAnimeListParam) = flowHandleRequest(dispatcher) {
        service.updateUserAnimeList(
            animeId = userAnimeList.animeId,
            status = userAnimeList.status,
            score = userAnimeList.score,
            numWatchedEpisodes = userAnimeList.numWatchedEpisodes,
            isRewatching = userAnimeList.isRewatching,
            priority = userAnimeList.priority,
            numTimesRewatched = userAnimeList.numTimesRewatched,
            rewatchValue = userAnimeList.rewatchValue,
            tags = null,
            comments = userAnimeList.comments
        )
    }
}