package com.alurwa.animerisuto.data.source.remote.response

import com.alurwa.animerisuto.model.Genre
import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 01/06/2021
 */

data class MangaResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("main_picture")
    val mainPicture: String?,

    @field:SerializedName("genres")
    val genres: List<Genre>,

    @field:SerializedName("mean")
    val mean: Float?
)
