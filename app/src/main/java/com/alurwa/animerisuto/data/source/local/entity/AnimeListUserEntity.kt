package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo

data class AnimeListUserEntity(

    @ColumnInfo(name = "my_list_status_is_rewatching")
    val isRewatching: Boolean? = null,

    @ColumnInfo(name = "my_list_status_score")
    val score: Int? = null,

    @ColumnInfo(name = "my_list_status_comments", defaultValue = "")
    val comments: String = "",

    @ColumnInfo(name = "my_list_status_update_at", defaultValue = "")
    val updatedAt: String = "",

    @ColumnInfo(name = "my_list_status_rewatch_value")
    val rewatchValue: Int? = null,

    @ColumnInfo(name = "my_list_status_priority")
    val priority: Int? = null,

    @ColumnInfo(name = "my_list_status_num_times_rewatched")
    val numTimesRewatched: Int? = null,

    @ColumnInfo(name = "my_list_status_num_watched_episodes")
    val numWatchedEpisodes: Int? = null,

    @ColumnInfo(name = "my_list_status_num_status", defaultValue = "")
    val status: String = "",

    @ColumnInfo(name = "my_list_status_num_tags")
    val tags: List<String>? = null
) {
    companion object {
        val EMPTY = AnimeListUserEntity(
            isRewatching = null,
            score = null,
            comments = "",
            updatedAt = "",
            rewatchValue = null,
            priority = null,
            numTimesRewatched = null,
            numWatchedEpisodes = null,
            status = "",
            tags = listOf()
        )
    }
}
