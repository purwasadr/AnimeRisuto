package com.alurwa.animerisuto.data.source.local.resultentity

import androidx.room.Embedded
import androidx.room.Relation
import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeSuggestionKeyEntity

/**
 * Created by Purwa Shadr Al 'urwa on 18/07/2021
 */

data class SuggestionKeysAndAnimeEntity(
    @Embedded
    val animeRemoteKeysEntity2: AnimeSuggestionKeyEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val animeEntity: AnimeEntity
)
