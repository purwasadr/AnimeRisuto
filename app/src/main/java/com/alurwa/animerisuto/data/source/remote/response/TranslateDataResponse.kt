package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TranslateDataResponse(
    @field:SerializedName("result")
    val result: String? = null,

    @field:SerializedName("origin")
    val origin: String? = null,

    @field:SerializedName("targets")
    val targets: List<Any?>? = null
)
