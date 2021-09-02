package com.alurwa.animerisuto.data.source.local.resultentity

import androidx.room.Embedded
import androidx.room.Relation
import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeSeasonalKeyEntity

data class SeasonalKeyAndAnimeEntity(
    @Embedded
    override val entry: AnimeSeasonalKeyEntity,

    @Relation(
        parentColumn = "anime_id",
        entityColumn = "id"
    )
    override val relation: AnimeEntity
) : EntryWithRelation<AnimeSeasonalKeyEntity, AnimeEntity>