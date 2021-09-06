package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Purwa Shadr Al 'urwa on 01/06/2021
 */

@Entity(tableName = "manga_remote_key")
data class MangaRemoteKeysEntity(
    @PrimaryKey val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
