package com.malabar.malabarmoviesapp.domain.repository.tv

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.video.MovieVideoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Path

class TvSeriesVideoRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveTvVideo(
        seriesId: Int
    ): Flow<Either<Failure, MovieVideoResponse>> = flow {
        try {
            emit(
                Either.Right(
                    movieApi.retrieveTvVideo(seriesId)
                )
            )
        } catch (ex: Exception) {
            emit(
                Either.Left(
                    Failure.ServerError(ex.cause)
                )
            )
        }
    }

    suspend fun retrieveTvSeasonVideo(
        seriesId: Int,
        seasonNumber: Int
    ): Flow<Either<Failure, MovieVideoResponse>> = flow {
        try {
            emit(
                Either.Right(
                    movieApi.retrieveTvSeasonVideo(seriesId, seasonNumber)
                )
            )
        } catch (ex: Exception) {
            emit(
                Either.Left(
                    Failure.ServerError(ex.cause)
                )
            )
        }
    }

    suspend fun retrieveTvEpisodeVideos(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): Flow<Either<Failure, MovieVideoResponse>> = flow {
        try {
            emit(
                Either.Right(
                    movieApi.retrieveTvEpisodeVideos(
                        seriesId, seasonNumber, episodeNumber
                    )
                )
            )
        } catch (ex: Exception) {
            emit(
                Either.Left(
                    Failure.ServerError(ex.cause)
                )
            )
        }
    }.flowOn(Dispatchers.IO)
}