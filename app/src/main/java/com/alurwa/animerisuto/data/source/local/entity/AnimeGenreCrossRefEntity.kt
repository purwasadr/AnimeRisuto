package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * Created by Purwa Shadr Al 'urwa on 15/07/2021
 */

@Entity(
    tableName = "anime_genre_cross",
    primaryKeys = ["anime_id", "genre_id"],
    foreignKeys = [
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["genre_id"],
            childColumns = ["genre_id"],
        )
    ]
)
data class AnimeGenreCrossRefEntity(
    @ColumnInfo(name = "anime_id")
    val animeId: Int,
    @ColumnInfo(name = "genre_id")
    val genreId: Int,
)
