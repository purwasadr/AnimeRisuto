package com.alurwa.animerisuto.utils

import com.alurwa.animerisuto.data.source.local.entity.AnimeEntity
import com.alurwa.animerisuto.data.source.local.entity.MangaEntity
import com.alurwa.animerisuto.data.source.remote.response.AnimeListResponse
import com.alurwa.animerisuto.data.source.remote.response.MangaListResponse

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

    /* fun animeResponseListToEntity2(input: AnimeSuggestions): List<AnimeEntity> =
         input.data.map {
             AnimeEntity(
                 id = it.node.id,
                 paging = input.paging.next,
                 title = it.node.title,
                 mainPicture = it.node.mainPicture?.let { mainPicture ->
                     MainPicture(mainPicture.large, mainPicture.medium)
                 },
                 genres = it.node.genres.map { genre ->
                     Genre(genre.id, genre.name)
                 },
                 mean = it.node.mean
             )
         }

     */

    // fun animeEntityListToDomain(input: List<AnimeEntity>): List<Anime> =
}
