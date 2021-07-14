package com.alurwa.animerisuto.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alurwa.animerisuto.model.User

/**
 * Created by Purwa Shadr Al 'urwa on 13/07/2021
 */

@Entity(tableName = "user")
data class UserEntity(
    val id: Int,
    val name: String,
    val picture: String?,
    val gender: String?,
    val birthday: String?,
    val location: String?,
    @ColumnInfo(name = "joined_at")
    val joinedAt: String,
    @ColumnInfo(name = "is_supporter")
    val isSupporter: Boolean?,
    @PrimaryKey
    val me: Int = 1,
) {
    val toUserDomain
        get() = User(
            id = id,
            name = name,
            picture = picture,
            gender = gender,
            birthday = birthday,
            location = location,
            joinedAt = joinedAt,
            isSupporter = isSupporter,
        )
}
