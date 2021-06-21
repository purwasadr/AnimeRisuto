package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alurwa.animerisuto.data.source.remote.response.StartSeasonResponse
import com.alurwa.animerisuto.model.StartSeason

/**
 * Created by Purwa Shadr Al 'urwa on 20/06/2021
 */

@Entity(tableName = "anime_detail")
data class AnimeDetailEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    @ColumnInfo(name = "main_picture_url")
    val mainPictureUrl: String?,
    @ColumnInfo(name = "num_episodes")
    val numEpisodes: Int,
    @ColumnInfo(name = "start_season") val startSeason: StartSeason?,
    val rank: Int?,
    val mean: Float?,
    val synopsis: String?,
)