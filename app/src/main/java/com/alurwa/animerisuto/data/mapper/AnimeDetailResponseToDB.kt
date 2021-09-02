package com.alurwa.animerisuto.data.mapper

import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import com.alurwa.animerisuto.data.source.remote.response.AnimeDetailResponse
import com.alurwa.animerisuto.model.AnimeRecommendation
import com.alurwa.animerisuto.model.StartSeason
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeDetailResponseToDB @Inject constructor() :
    Mapper<AnimeDetailResponse, AnimeDetailEntity> {
    override suspend fun map(from: AnimeDetailResponse) =
        AnimeDetailEntity(
            id = from.id,
            title = from.title,
            mainPictureUrl = from.mainPicture?.medium,
            numEpisodes = from.numEpisodes,
            startSeason = from.startSeason?.let {
                StartSeason(it.year, it.season)
            },
            rank = from.rank,
            mean = from.mean,
            synopsis = from.synopsis,
            recommendations = from.recommendations.map {
                AnimeRecommendation(it.node.id, it.node.title, it.node.mainPicture?.medium)
            },
            mediaType = from.mediaType,
            status = from.status,
            studios = from.studios.map {
                it.name
            },
            source = from.source,
            genres = from.genres?.map {
                it.name
            },
            englishTitle = from.alternativeTitles?.en
        )

}