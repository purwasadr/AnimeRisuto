package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
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
    ],
    foreignKeys = [
        ForeignKey(
            entity = AnimeDetailEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("anime_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class AnimeRankingKeyEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0,
    val type: String,
    @ColumnInfo(name = "anime_id")
    val animeId: Int,
    @ColumnInfo(name = "prev_key")
    override val prevKey: Int?,
    @ColumnInfo(name = "next_key")
    override val nextKey: Int?
) : IKeyEntity {
    companion object {
        const val TABLE_NAME = "anime_ranking_remote_key"
    }
}
