package com.alurwa.animerisuto.model

/**
 * Created by Purwa Shadr Al 'urwa on 18/06/2021
 */

data class AnimeDetail(
    val id: Int,
    val title: String,
    val mainPictureUrl: String?,
    val numEpisodes: Int,
    val startSeason: StartSeason?,
    val rank: Int?,
    val mean: Float?,
    val synopsis: String?,
    val recommendations: List<AnimeRecommendation>,
    val mediaType: String,
    val status: String,
    val studios: List<String>,
    val source: String?,
    val genres: List<String>?,
    val englishTitle: String?,
)
