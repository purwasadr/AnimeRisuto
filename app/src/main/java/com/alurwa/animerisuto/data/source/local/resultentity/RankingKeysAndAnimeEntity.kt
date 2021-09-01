package com.alurwa.animerisuto.data.source.local.resultentity

import androidx.room.Embedded
import androidx.room.Relation
import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeRankingKeyEntity

/**
 * Menerapkan [EntryWithRelation] agar dapat digunakan untuk [RemoteMediator]
 */
data class RankingKeysAndAnimeEntity(
    @Embedded
    override val entry: AnimeRankingKeyEntity,

    @Relation(
        parentColumn = "anime_id",
        entityColumn = "id"
    )
    override val relation: AnimeEntity
) : EntryWithRelation<AnimeRankingKeyEntity, AnimeEntity>
