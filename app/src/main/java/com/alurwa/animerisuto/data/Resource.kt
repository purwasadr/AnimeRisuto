package com.alurwa.animerisuto.data

/**
 * Created by Purwa Shadr Al 'urwa on 08/05/2021
 */

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
