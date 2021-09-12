package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AnimeListStatusResponse(

	@field:SerializedName("is_rewatching")
	val isRewatching: Boolean? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("comments")
	val comments: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("rewatch_value")
	val rewatchValue: Int? = null,

	@field:SerializedName("priority")
	val priority: Int? = null,

	@field:SerializedName("num_times_rewatched")
	val numTimesRewatched: Int? = null,

	@field:SerializedName("num_watched_episodes")
	val numWatchedEpisodes: Int? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("tags")
	val tags: List<String>? = null
)
