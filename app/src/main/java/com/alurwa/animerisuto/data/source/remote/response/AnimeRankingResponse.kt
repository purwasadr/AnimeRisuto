package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 17/07/2021
 */

data class AnimeRankingResponse(
    @field:SerializedName("data")
    val data: List<AnimeListRankedResponse>,

    @field:SerializedName("paging")
    val paging: PagingResponse
)
