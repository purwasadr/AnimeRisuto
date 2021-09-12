package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo

data class UserAnimeListEntity(
    val id: Int,
    val title: String = "",
    @ColumnInfo(name = "main_picture_url")
    val mainPictureUrl: String? = null,
    @ColumnInfo(name = "my_list_status_num_watched_episodes")
    val num_episode_watched: Int? = null,
    @ColumnInfo(name = "num_episodes")
    val numEpisodes: Int = 0,
    @ColumnInfo(name = "my_list_status_score")
    val list_status_score: Int? = null,
    @ColumnInfo(name = "my_list_status_num_status")
    val list_status_status: String = ""
)
