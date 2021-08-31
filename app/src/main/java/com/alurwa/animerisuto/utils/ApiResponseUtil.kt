package com.alurwa.animerisuto.utils

import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <T : Any> flowHandleRequest(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    crossinline requestFunc: suspend () -> T
): Flow<ApiResponse<T>> = flow {
    try {
        val request = requestFunc.invoke()

        if (request is List<*>) {
            if (request.isNotEmpty()) {
                emit(ApiResponse.Success(request))
            } else {
                emit(ApiResponse.Empty)
            }
        } else {
            emit(ApiResponse.Success(request))
        }

    } catch (e: Exception) {
        emit(ApiResponse.Error(e.toString()))
    }
}.flowOn(dispatcher)