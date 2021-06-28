package com.alurwa.animerisuto.data.source.local.room

import androidx.room.TypeConverter
import com.alurwa.animerisuto.model.AnimeRecommendation
import com.alurwa.animerisuto.model.Genre
import com.alurwa.animerisuto.model.MainPicture
import com.alurwa.animerisuto.model.StartSeason
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.StringJoiner

/**
 * Created by Purwa Shadr Al 'urwa on 17/05/2021
 */

class Converters {

    @TypeConverter
    fun fromStartSeason(value: StartSeason?): String? {
        val gson = Gson()
        val type = object : TypeToken<StartSeason?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStartSeason(value: String?): StartSeason? {
        val gson = Gson()
        val type = object : TypeToken<StartSeason?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromAnimeRecommendations(value: List<AnimeRecommendation>): String {
        val gson = Gson()
        val type = object : TypeToken<List<AnimeRecommendation>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toAnimeRecommendations(value: String): List<AnimeRecommendation> {
        val gson = Gson()
        val type = object : TypeToken<List<AnimeRecommendation>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return value.split(',')
    }
}
