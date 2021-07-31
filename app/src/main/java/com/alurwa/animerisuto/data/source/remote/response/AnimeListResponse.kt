package com.alurwa.animerisuto.data.source.remote.response

import com.alurwa.animerisuto.data.source.local.entity.AnimeGenreCrossRefEntity
import com.alurwa.animerisuto.data.source.local.entity.GenreEntity
import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 16/05/2021
 */

data class AnimeListResponse(

    @field:SerializedName("node")
    val node: AnimeResponse
)

fun List<AnimeListResponse>.getGenreEntitySet(): Set<GenreEntity> {
    val tempSet = mutableSetOf<GenreEntity>()
    this.forEach {
        it.node.genres?.forEach { genresRes ->
            tempSet.add(GenreEntity(genresRes.id, genresRes.name))
        }
    }

    return tempSet
}

fun List<AnimeListResponse>.getGenreCrossList(): List<AnimeGenreCrossRefEntity> {
    val tempList = mutableListOf<AnimeGenreCrossRefEntity>()
    this.forEach {
        it.node.genres?.forEach { genresRes ->
            tempList.add(AnimeGenreCrossRefEntity(it.node.id, genresRes.id))
        }
    }

    return tempList
}
