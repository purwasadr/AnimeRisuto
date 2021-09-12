package com.alurwa.animerisuto.data.source.local.resultentity

import androidx.room.Embedded
import androidx.room.Relation
import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import com.alurwa.animerisuto.data.source.local.entity.UserAnimeListEntity
import com.alurwa.animerisuto.data.source.local.entity.UserAnimeListKeyEntity

data class UserAnimeListKeyAndDB(
    @Embedded
    override val entry: UserAnimeListKeyEntity,

    @Relation(
        parentColumn = "relation_id",
        entityColumn = "id",
        entity = AnimeDetailEntity::class
    )
    override val relation: UserAnimeListEntity
) : EntryWithRelation<UserAnimeListKeyEntity, UserAnimeListEntity>