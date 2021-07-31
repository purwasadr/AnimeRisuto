package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Purwa Shadr Al 'urwa on 18/07/2021
 */

@Entity(tableName = AnimeSuggestionKeyEntity.TABLE_NAME)
data class AnimeSuggestionKeyEntity(
    @PrimaryKey
    val id: Int,
    val no: Int,
    @ColumnInfo(name = "prev_key")
    val prevKey: Int?,
    @ColumnInfo(name = "next_key")
    val nextKey: Int?
) {
    companion object {
        const val TABLE_NAME = "anime_suggestion_remote_key"
    }
}