package com.alurwa.animerisuto.data.source.remote.response

import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity
import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 17/07/2021
 */

data class AnimeListRankedResponse(
    @field:SerializedName("node")
    val node: AnimeResponse,
    @field:SerializedName("ranking")
    val ranking: RankingResponse
)

fun List<AnimeListRankedResponse>.toEntityWithPaging(offset: Int): List<AnimeEntity> =
    this.mapIndexed { index, it ->
        AnimeEntity(
            id = it.node.id,
            title = it.node.title,
            posterPath = it.node.mainPicture?.medium,
            genres = it.node.genres?.map { genre ->
                genre.name
            },
            mean = it.node.mean
        )
    }
