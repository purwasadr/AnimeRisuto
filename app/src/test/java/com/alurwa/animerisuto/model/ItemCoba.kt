package com.alurwa.animerisuto.model

/**
 * Created by Purwa Shadr Al 'urwa on 01/06/2021
 */

data class ItemCoba(
    override val id: Int,
    override val title: String,
    override val posterPath: String?
) : ItemForList() {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is ItemCoba -> other.id == id && other.title == title && other.posterPath == posterPath
            is ItemCoba2 -> other.id == id && other.title == title && other.posterPath == posterPath
            else -> false
        }
    }
}
