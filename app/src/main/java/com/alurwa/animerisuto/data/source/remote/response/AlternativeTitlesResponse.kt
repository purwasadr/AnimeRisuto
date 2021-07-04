package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 04/07/2021
 */

data class AlternativeTitlesResponse(
    @field:SerializedName("synonyms")
    val synonyms: List<String>?,

    @field:SerializedName("en")
    val en: String?,

    @field:SerializedName("ja")
    val ja: String?,
)
