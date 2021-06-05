package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 01/06/2021
 */

data class MangaRankingResponse(
    @field:SerializedName("data")
    val data: List<MangaListResponse>,

    @field:SerializedName("paging")
    val paging: PagingResponse
)
