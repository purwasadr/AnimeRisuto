package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserAnimeListPagingResponse(
    @field:SerializedName("node")
    val node: AnimeResponse,

    @field:SerializedName("list_status")
    val animeListStatus: AnimeListStatusResponse? =  null
)