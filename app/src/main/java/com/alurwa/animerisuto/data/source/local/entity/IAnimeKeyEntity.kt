package com.alurwa.animerisuto.data.source.local.entity

/**
 * Digunakan untuk membuat data class Remote Key untuk RemoteMediator
 */
interface IAnimeKeyEntity {
    val id: Long
    val animeId: Int
    val prevKey: Int?
    val nextKey: Int?
}
