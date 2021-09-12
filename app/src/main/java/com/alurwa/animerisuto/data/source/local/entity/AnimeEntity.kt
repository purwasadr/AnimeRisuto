package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo

/**
 * Created by Purwa Shadr Al 'urwa on 15/05/2021
 */

data class AnimeEntity(
    val id: Int,

    val title: String = "",

    // Poster path is fill from MainPicture.medium backend
    @ColumnInfo(name = "main_picture_url")
    val posterPath: String? = null,

    val genres: List<String>? = null,

    val mean: Float? = null
)
