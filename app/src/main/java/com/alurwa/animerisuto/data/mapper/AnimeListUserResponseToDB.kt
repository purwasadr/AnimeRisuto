package com.alurwa.animerisuto.data.mapper

import com.alurwa.animerisuto.data.source.local.entity.AnimeListUserEntity
import com.alurwa.animerisuto.data.source.remote.response.AnimeListStatusResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeListUserResponseToDB @Inject constructor() : Mapper<AnimeListStatusResponse, AnimeListUserEntity> {
    override suspend fun map(from: AnimeListStatusResponse): AnimeListUserEntity =
        from.run {
            AnimeListUserEntity(
                isRewatching = isRewatching,
                score = score,
                comments = comments.orEmpty(),
                updatedAt = updatedAt.orEmpty(),
                rewatchValue = rewatchValue,
                priority = priority,
                numTimesRewatched = numTimesRewatched,
                numWatchedEpisodes = numEpisodesWatched,
                status = status.orEmpty(),
                tags = tags.orEmpty()
            )
        }
}