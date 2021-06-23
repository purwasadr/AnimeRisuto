package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 21/06/2021
 */

data class StartSeasonResponse(
    @field:SerializedName("year")
    val year: Int,
    @field:SerializedName("season")
    val season: String
)
