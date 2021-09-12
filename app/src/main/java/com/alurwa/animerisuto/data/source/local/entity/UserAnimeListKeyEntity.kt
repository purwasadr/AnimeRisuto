package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_anime_list_key",
    foreignKeys = [
        ForeignKey(
            entity = AnimeDetailEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("relation_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class UserAnimeListKeyEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,
    @ColumnInfo(name = "relation_id")
    val relationId: Int,
    override val prevKey: Int?,
    override val nextKey: Int?
) : IKeyEntity
