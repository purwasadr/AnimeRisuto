package com.alurwa.animerisuto.model

data class UserAnimeList(
    val id: Int,
    val title: String,
    val posterPath: String,
    val num_episode_watched: Int,
    val total_episode: Int,
    val score: Int,
    val status: String
)
