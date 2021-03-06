package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 19/06/2021
 */

data class AnimeDetailResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("main_picture")
    val mainPicture: MainPictureResponse?,

    @field:SerializedName("num_episodes")
    val numEpisodes: Int,

    @field:SerializedName("start_season")
    val startSeason: StartSeasonResponse?,

    @field:SerializedName("rank")
    val rank: Int?,

    @field:SerializedName("mean")
    val mean: Float?,

    @field:SerializedName("synopsis")
    val synopsis: String?,

    @field:SerializedName("recommendations")
    val recommendations: List<AnimeDetailRecommendationsResponse>,

    @field:SerializedName("media_type")
    val mediaType: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("studios")
    val studios: List<StudioResponse>,

    @field:SerializedName("source")
    val source: String?,

    @field:SerializedName("genres")
    val genres: List<GenreResponse>?,

    @field:SerializedName("alternative_titles")
    val alternativeTitles: AlternativeTitlesResponse?,
)
