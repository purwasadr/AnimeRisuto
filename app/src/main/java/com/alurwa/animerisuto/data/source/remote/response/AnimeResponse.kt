package com.alurwa.animerisuto.data.source.remote.response

import com.alurwa.animerisuto.model.Anime
import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 16/05/2021
 */

data class AnimeResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("main_picture")
    val mainPicture: MainPictureResponse?,

    @field:SerializedName("genres")
    val genres: List<GenreResponse>?,

    @field:SerializedName("mean")
    val mean: Float?
) {

    companion object {

        val List<AnimeListResponse>.toAnimeDomain
            get() = this.map {
                Anime(
                    id = it.node.id,
                    title = it.node.title,
                    posterPath = it.node.mainPicture?.medium,
                    genres = it.node.genres?.map { genre ->
                        genre.name
                    },
                    mean = it.node.mean
                )
            }
    }
}
