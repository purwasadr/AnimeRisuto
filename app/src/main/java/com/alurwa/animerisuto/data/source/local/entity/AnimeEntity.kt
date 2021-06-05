package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Purwa Shadr Al 'urwa on 15/05/2021
 */

@Entity(tableName = "anime_list")
data class AnimeEntity(

    @PrimaryKey
    val id: Int,

    val no: Int,
    // var paging: Int = 0,

    val title: String,

    // Poster path is fill from MainPicture.medium backend
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    val genres: List<String>,

    val mean: Float?
)
