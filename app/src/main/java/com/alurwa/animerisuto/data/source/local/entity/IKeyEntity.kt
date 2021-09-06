package com.alurwa.animerisuto.data.source.local.entity

/**
 * Digunakan untuk membuat data class Remote Key untuk RemoteMediator
 */
interface IKeyEntity {
    val id: Long
    val prevKey: Int?
    val nextKey: Int?
}
