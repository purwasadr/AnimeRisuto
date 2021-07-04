package com.alurwa.animerisuto.model

/**
 * Created by Purwa Shadr Al 'urwa on 12/05/2021
 */

data class Anime(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val genres: List<String>?,
    val mean: Float?,
    val no: Int? = null
)
