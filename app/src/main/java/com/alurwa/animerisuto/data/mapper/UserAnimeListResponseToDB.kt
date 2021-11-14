package com.alurwa.animerisuto.data.mapper

import com.alurwa.animerisuto.data.source.local.entity.UserAnimeListEntity
import com.alurwa.animerisuto.data.source.remote.response.UserAnimeListPagingResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAnimeListResponseToDB @Inject constructor(

) : Mapper<UserAnimeListPagingResponse, UserAnimeListEntity> {
    override suspend fun map(from: UserAnimeListPagingResponse): UserAnimeListEntity =
        from.run {
            UserAnimeListEntity(
                id = node.id,
                title = node.title,
                mainPictureUrl = node.mainPicture?.medium.orEmpty(),
                num_episode_watched = animeListStatus?.numEpisodesWatched ?: 0 ,
                numEpisodes = node.numEpisodes ?: 0,
                list_status_score = animeListStatus?.score,
                list_status_status = animeListStatus?.status.orEmpty()
            )
        }
}