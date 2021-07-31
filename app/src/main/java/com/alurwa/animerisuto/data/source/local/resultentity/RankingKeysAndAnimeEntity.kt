package com.alurwa.animerisuto.data.source.local.resultentity

import androidx.room.Embedded
import androidx.room.Relation
import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeRankingKeyEntity

/**
 * Created by Purwa Shadr Al 'urwa on 18/07/2021
 */

data class RankingKeysAndAnimeEntity(
    @Embedded
    override val entry: AnimeRankingKeyEntity,

    @Relation(
        parentColumn = "anime_id",
        entityColumn = "id"
    )
    override val relation: AnimeEntity
) : EntryWithAnime<AnimeRankingKeyEntity>
