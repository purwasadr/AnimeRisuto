package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 16/05/2021
 */

data class AnimeResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("main_picture")
    val mainPicture: MainPictureResponse?,

    @field:SerializedName("genres")
    val genres: List<GenreResponse>?,

    @field:SerializedName("mean")
    val mean: Float?
)
