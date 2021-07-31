package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 17/07/2021
 */

data class RankingResponse(
    @field:SerializedName("rank")
    val rank: Int,
    @field:SerializedName("previous_rank")
    val previousRank: Int?
)
