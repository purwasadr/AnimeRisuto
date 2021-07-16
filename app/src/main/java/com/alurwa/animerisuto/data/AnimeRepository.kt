package com.alurwa.animerisuto.data

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.alurwa.animerisuto.data.source.local.ILocalDataSource
import com.alurwa.animerisuto.data.source.local.room.AnimeRisutoDatabase
import com.alurwa.animerisuto.data.source.remote.IRemoteDataSource
import com.alurwa.animerisuto.data.source.remote.network.ApiResponse
import com.alurwa.animerisuto.data.source.remote.network.ApiService
import com.alurwa.animerisuto.data.source.remote.response.AnimeDetailResponse
import com.alurwa.animerisuto.data.source.remote.response.UserResponse
import com.alurwa.animerisuto.model.Anime
import com.alurwa.animerisuto.model.AnimeDetail
import com.alurwa.animerisuto.model.User
import com.alurwa.animerisuto.utils.DataMapper
import com.alurwa.animerisuto.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService,
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource,
    private val animeRisutoDatabase: AnimeRisutoDatabase
) : IAnimeRepository {

    override fun getAccessToken(
        code: String,
        codeVerifier: String
    ) = flow<Resource<Boolean>> {
        emit(Resource.Loading())
        val response = remoteDataSource.getAccesToken(code, codeVerifier)
        when (val apiResponse = response.first()) {
            is ApiResponse.Success -> {
                val responseData = apiResponse.data

                SessionManager(context).saveAuth(
                    responseData.accessToken,
                    responseData.refreshToken,
                    true
                )

                emit(Resource.Success(true))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getAnimeList(): Flow<PagingData<Anime>> {
        val pagingSourceFactory = { animeRisutoDatabase.animeDao().getAnimeFull() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = AnimeRemoteMediator2(
                apiService,
                animeRisutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { anime ->
                Anime(
                    anime.anime.animeId,
                    anime.anime.title,
                    anime.anime.posterPath,
                    anime.genres.map { genre -> genre.name },
                    anime.anime.mean
                )
            }
        }
    }

    override fun getMangaPaging(): Flow<PagingData<Anime>> {
        val pagingSourceFactory = { animeRisutoDatabase.mangaDao().getMangaList() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = AnimeRemoteMediator(
                apiService,
                animeRisutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { anime ->
                Anime(anime.id, anime.title, anime.posterPath, anime.genres, anime.mean)
            }
        }
    }

    override fun getAnimeDetails(id: Int): Flow<Resource<AnimeDetail?>> =
        object : NetworkBoundResource<AnimeDetail?, AnimeDetailResponse>() {
            override fun loadFromDB(): Flow<AnimeDetail?> =
                localDataSource.getAnimeDetail(id).map {
                    it?.let { entity ->
                        DataMapper.animeDetailEntityToDomain(entity)
                    }
                }

            override fun shouldFetch(): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<AnimeDetailResponse>> =
                remoteDataSource.getAnimeDetails(id)

            override suspend fun saveCallResult(data: AnimeDetailResponse) {
                val entity = DataMapper.animeDetailResponseToEntity(data)
                localDataSource.insertAnimeDetail(entity)
            }
        }.asFlow()

    override fun getAnimeSearch(query: String): Flow<PagingData<Anime>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { AnimePagingSource(apiService, query) }
        ).flow

    override fun getUser(): Flow<Resource<User?>> =
        object : NetworkBoundResource<User?, UserResponse>() {
            override fun loadFromDB(): Flow<User?> =
                localDataSource.getUser().map {
                    it?.toUserDomain
                }

            override fun shouldFetch(): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> =
                remoteDataSource.getUser()

            override suspend fun saveCallResult(data: UserResponse) {
                localDataSource.insertUser(data.toUserEntity)
            }
        }.asFlow()
}
