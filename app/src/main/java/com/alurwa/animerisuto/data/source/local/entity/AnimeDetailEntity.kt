package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alurwa.animerisuto.model.AnimeRecommendation
import com.alurwa.animerisuto.model.StartSeason

/**
 * Created by Purwa Shadr Al 'urwa on 20/06/2021
 */

@Entity(tableName = "anime_detail")
data class AnimeDetailEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(defaultValue = "")
    val title: String = "",
    @ColumnInfo(name = "main_picture_url")
    val mainPictureUrl: String? = null,
    @ColumnInfo(name = "num_episodes", defaultValue = "0")
    val numEpisodes: Int = 0,
    @ColumnInfo(name = "start_season")
    val startSeason: StartSeason? = null,
    val rank: Int? = null,
    val mean: Float? = null,
    val synopsis: String? = null,
    val recommendations: List<AnimeRecommendation>? = null,

    @ColumnInfo(name = "media_type", defaultValue = "")
    val mediaType: String,
    @ColumnInfo(defaultValue = "")
    val status: String,
    val studios: List<String>? = null,
    val source: String? = null,
    val genres: List<String>? = null,

    @ColumnInfo(name = "english_title")
    val englishTitle: String? = null,

    @Embedded
    val animeListUserEntity: AnimeListUserEntity
)
