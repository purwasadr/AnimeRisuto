package com.alurwa.animerisuto.data

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    companion object {
        fun <T> errorMessage(message: String): Result<T> {
            return Error(Exception(message))
        }
    }
}

inline fun <T> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> {
    if (this is Result.Success) action(this.data)
    return this
}

inline fun <T> Result<T>.onError(action: (value: Exception) -> Unit): Result<T> {
    if (this is Result.Error) action(this.exception)
    return this
}

inline fun <T> Result<T>.onLoading(action: () -> Unit): Result<T> {
    if (this is Result.Loading) action()
    return this
}

inline fun <T> Result<T>.onNotLoading(action: () -> Unit): Result<T> {
    if (this !is Result.Loading) action()
    return this
}
