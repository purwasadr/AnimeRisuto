package com.alurwa.animerisuto.data.source.local.entity

/**
 * Created by Purwa Shadr Al 'urwa on 21/07/2021
 */

interface IAnimeKeyEntity {
    val id: Long
    val animeId: Int
    val prevKey: Int?
    val nextKey: Int?
}
