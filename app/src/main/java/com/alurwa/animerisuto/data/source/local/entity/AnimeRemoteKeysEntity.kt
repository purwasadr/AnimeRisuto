package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Purwa Shadr Al 'urwa on 16/05/2021
 */

@Entity(tableName = "anime_remote_keys")
data class AnimeRemoteKeysEntity(
    // @PrimaryKey
    // val id: Int,

    @PrimaryKey
    val name: String = "suggestion",

    @ColumnInfo(name = "next_key")
    val nextKey: Int?
)
