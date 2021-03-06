package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.alurwa.animerisuto.data.source.local.entity.AnimeSeasonalKeyEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index("anime_id"),
        Index("type")
    ]
)
data class AnimeSeasonalKeyEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0,
    val type: String,
    val year: Int,
    @ColumnInfo(name = "anime_id")
    val animeId: Int,
    @ColumnInfo(name = "prev_key")
    override val prevKey: Int?,
    @ColumnInfo(name = "next_key")
    override val nextKey: Int?

) : IKeyEntity {
    companion object {
        const val TABLE_NAME = "anime_seasonal_remote_key"
    }
}