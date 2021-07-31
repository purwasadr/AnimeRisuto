package com.alurwa.animerisuto.data.source.local.resultentity

import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity

interface EntryWithAnime<T> {
    val entry: T
    val relation: AnimeEntity
}
