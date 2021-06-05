package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Purwa Shadr Al 'urwa on 01/06/2021
 */

@Entity(tableName = "manga_list")
data class MangaEntity(

    @PrimaryKey
    val id: Int,

    val no: Int,

    val title: String,

    // Poster path is fill from MainPicture.medium backend
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    val genres: List<String>,

    val mean: Float?
)
