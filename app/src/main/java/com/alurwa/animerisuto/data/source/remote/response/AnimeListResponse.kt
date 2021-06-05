package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 16/05/2021
 */

data class AnimeListResponse(

    @field:SerializedName("node")
    val node: AnimeNodeResponse
)
