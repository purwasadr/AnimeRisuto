package com.alurwa.animerisuto.data.source.local.room

import androidx.room.TypeConverter
import com.alurwa.animerisuto.model.Genre
import com.alurwa.animerisuto.model.MainPicture
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Purwa Shadr Al 'urwa on 17/05/2021
 */

class Converters {

    @TypeConverter
    fun fromGenres(value: List<Genre>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGenres(value: String): List<Genre> {
        val gson = Gson()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromMainPicture(value: MainPicture): String {
        val gson = Gson()
        val type = object : TypeToken<MainPicture>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toMainPicture(value: String): MainPicture {
        val gson = Gson()
        val type = object : TypeToken<MainPicture>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStrings(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStrings(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}
