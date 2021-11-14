package com.alurwa.animerisuto.data

import com.alurwa.animerisuto.data.Result
import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<Result<ResultType>> = flow {
        emit(Result.Loading)
        if (shouldFetch()) {
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Result.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Result.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Result.errorMessage(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDB().map { Result.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Result<ResultType>> = result
}
