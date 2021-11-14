package com.alurwa.animerisuto.data.repository.translate

import com.alurwa.animerisuto.data.Result
import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.model.Translate
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TranslateRepository @Inject constructor(
    private val remoteSource: TranslateRemoteSource
) {
    fun getTranslateToIndo(text: String) = remoteSource.getTranslateToIndo(text).map {
        if (it is ApiResponse.Success) {
            Result.Success(Translate(it.data.data?.result.orEmpty()))
        } else if (it is ApiResponse.Error) {
            Result.errorMessage(it.errorMessage)
        } else {
            Result.errorMessage("Error")
        }
    }
}