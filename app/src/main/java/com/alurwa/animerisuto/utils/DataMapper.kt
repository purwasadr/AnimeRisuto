package com.alurwa.animerisuto.utils

import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity
import com.alurwa.animerisuto.data.source.local.entity.MangaEntity
import com.alurwa.animerisuto.data.source.remote.response.AnimeDetailResponse
import com.alurwa.animerisuto.data.source.remote.response.AnimeListResponse
import com.alurwa.animerisuto.data.source.remote.response.MangaListResponse
import com.alurwa.animerisuto.model.AnimeDetail
import com.alurwa.animerisuto.model.AnimeRecommendation
import com.alurwa.animerisuto.model.StartSeason

/**
 * Created by Purwa Shadr Al 'urwa on 16/05/2021
 */

object DataMapper {

    fun animeResponseListToEntityWithPaging(
        input: List<AnimeListResponse>,
        paging: Int
    ): List<AnimeEntity> =
        input.mapIndexed { index, it ->
            AnimeEntity(
                id = it.node.id,
                no = paging + index,
                title = it.node.title,
                posterPath = it.node.mainPicture?.medium,
                genres = it.node.genres.map { genre ->
                    genre.name
                },
                mean = it.node.mean
            )
        }

    fun mangaResponseListToEntityWithPaging(
        input: List<MangaListResponse>,
        paging: Int
    ): List<MangaEntity> =
        input.mapIndexed { index, it ->
            MangaEntity(
                id = it.node.id,
                no = paging + index,
                title = it.node.title,
                posterPath = it.node.mainPicture?.medium,
                genres = it.node.genres.map { genre ->
                    genre.name
                },
                mean = it.node.mean
            )
        }

    fun animeDetailResponseToEntity(
        input: AnimeDetailResponse
    ): AnimeDetailEntity =
        AnimeDetailEntity(
            id = input.id,
            title = input.title,
            mainPictureUrl = input.mainPicture?.medium,
            numEpisodes = input.numEpisodes,
            startSeason = input.startSeason?.let {
                StartSeason(it.year, it.season)
            },
            rank = input.rank,
            mean = input.mean,
            synopsis = input.synopsis,
            recommendations = input.recommendations.map {
                AnimeRecommendation(it.node.id, it.node.title, it.node.mainPicture?.medium)
            }

        )

    fun animeDetailEntityToDomain(
        input: AnimeDetailEntity
    ): AnimeDetail =
        input.run {
            AnimeDetail(
                id = id,
                title = title,
                mainPictureUrl = mainPictureUrl,
                numEpisodes = input.numEpisodes,
                startSeason = input.startSeason,
                rank = input.rank,
                mean = mean,
                synopsis = synopsis,
                recommendations = recommendations
            )
        }
}
