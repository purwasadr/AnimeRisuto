package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 16/05/2021
 */

data class AnimeSuggestionsResponse(

    @field:SerializedName("data")
    val data: List<AnimeListResponse>,

    @field:SerializedName("paging")
    val paging: PagingResponse
)
