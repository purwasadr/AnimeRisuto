package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 20/06/2021
 */

data class AnimeDetailRecommendationsResponse(
    @field:SerializedName("node")
    val node: AnimeResponse
)
