package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * Created by Purwa Shadr Al 'urwa on 15/07/2021
 */

data class AnimeAndGenres(

    @Embedded
    val anime: AnimeEntity,

    @Relation(
        parentColumn = "anime_id",
        entityColumn = "genre_id",
        associateBy = Junction(AnimeGenreCrossRefEntity::class)
    )
    val genres: List<GenreEntity>
)
