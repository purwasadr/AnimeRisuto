package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserAnimeListResponse(
    @field:SerializedName("data")
    val data: List<UserAnimeListPagingResponse>,

    @field:SerializedName("paging")
    val paging: PagingResponse
)