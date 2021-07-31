package com.alurwa.animerisuto.data.source.local.resultentity

interface EntryWithRelation<T, S> {
    val entry: T
    val relation: S
}
