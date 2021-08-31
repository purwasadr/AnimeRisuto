package com.alurwa.animerisuto.data.repository.animedetail

import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeDetailLocalSource @Inject constructor(
    private val database: AnimeRisutoDatabase
) {

    private val animeDetailDao = database.animeDetailDao()

    suspend fun insertAnimeDetail(animeDetail: AnimeDetailEntity) {
        animeDetailDao.insert(animeDetail)
    }

    fun getAnimeDetail(animeId: Int): Flow<AnimeDetailEntity?> =
        animeDetailDao.getAnimeDetail(animeId)
}