package com.alurwa.animerisuto.data.source.remote.network

/**
 * Created by Purwa Shadr Al 'urwa on 04/05/2021
 */

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}
