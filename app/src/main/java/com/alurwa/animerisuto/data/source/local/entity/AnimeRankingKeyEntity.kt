package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Purwa Shadr Al 'urwa on 18/07/2021
 */

@Entity(
    tableName = AnimeRankingKeyEntity.TABLE_NAME,
    indices = [
        Index("anime_id"),
        Index("type")
    ]
)
data class AnimeRankingKeyEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0,
    val type: String,
    @ColumnInfo(name = "anime_id")
    override val animeId: Int,
    @ColumnInfo(name = "prev_key")
    override val prevKey: Int?,
    @ColumnInfo(name = "next_key")
    override val nextKey: Int?
) : IAnimeKeyEntity {
    companion object {
        const val TABLE_NAME = "anime_ranking_remote_key"
    }
}
