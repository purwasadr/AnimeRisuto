package com.alurwa.animerisuto.data.repository.translate

import com.alurwa.animerisuto.data.Resource
import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.model.Translate
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TranslateRepository @Inject constructor(
    private val remoteSource: TranslateRemoteSource
) {
    fun getTranslateToIndo(text: String) = remoteSource.getTranslateToIndo(text).map {
        if (it is ApiResponse.Success) {
            Resource.Success(Translate(it.data.data?.result.orEmpty()))
        } else if (it is ApiResponse.Error) {
            Resource.Error<Translate>(it.errorMessage)
        } else {
            Resource.Error<Translate>("Error")
        }
    }
}