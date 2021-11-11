package com.alurwa.animerisuto.data.repository.translate

import com.alurwa.animerisuto.data.source.remote.network.TranslateService
import com.alurwa.animerisuto.di.DispatchersIO
import com.alurwa.animerisuto.utils.flowHandleRequest
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class TranslateRemoteSource @Inject constructor(
    private val service: TranslateService,
    @DispatchersIO
    private val ioDispatcher: CoroutineDispatcher
) {

    fun getTranslateToIndo(text: String) = flowHandleRequest(ioDispatcher) {
            service.getTranslate(text = text, to = "id")
    }
}