package com.alurwa.animerisuto.model

/**
 * Created by Purwa Shadr Al 'urwa on 19/07/2021
 */

data class ItemCoba3(
    override val nextKey: Int?,
    override val prevKey: Int?,
    val tambahNeh: String
) : IItemForList
