package com.alurwa.animerisuto.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Purwa Shadr Al 'urwa on 19/07/2021
 */

@RunWith(AndroidJUnit4::class)
class DatabaseTest3 {
    private lateinit var db: AnimeRisutoDatabase
//    private lateinit var genreDao: GenreDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AnimeRisutoDatabase::class.java
        ).build()
//        genreDao = db.AnimeDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadFilmDetail() = runBlocking {
//        val genreEntity = GenreEntity(1, "Romance")
//        val genreEntity2 = GenreEntity(2, "Romance")
//
//        db.genreDao().insertAll(setOf(genreEntity))
//
//        val genre = db.genreDao().getGenre()
//
//        MatcherAssert.assertThat(genre, Matchers.`is`(genreEntity))
//        println(genre == genreEntity)
    }
}
