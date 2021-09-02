package com.alurwa.animerisuto.data.mapper

import com.alurwa.animerisuto.data.source.local.entity.AnimeDetailEntity
import com.alurwa.animerisuto.model.AnimeDetail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeDetailDBToDomain @Inject constructor() : Mapper<AnimeDetailEntity, AnimeDetail> {
    override suspend fun map(from: AnimeDetailEntity) =
        from.run {
            AnimeDetail(
                id = id,
                title = title,
                mainPictureUrl = mainPictureUrl,
                numEpisodes = this.numEpisodes,
                startSeason = this.startSeason,
                rank = this.rank,
                mean = mean,
                synopsis = synopsis,
                recommendations = recommendations,
                mediaType = mediaType,
                status = status,
                studios = studios,
                source = source,
                genres = genres,
                englishTitle = englishTitle
            )
        }

}
