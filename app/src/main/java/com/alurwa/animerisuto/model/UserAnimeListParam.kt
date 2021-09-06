package com.alurwa.animerisuto.model

data class UserAnimeListParam(
    val animeId: Int,
    val status: String,
    val score: Int,
    val numWatchedEpisodes: Int,
    val comments: String? = null,
    val isRewatching: Boolean? = null,
    val updatedAt: String? = null,
    val rewatchValue: Int? = null,
    val priority: Int? = null,
    val numTimesRewatched: Int? = null,
    val tags: List<String>? = null,
)
