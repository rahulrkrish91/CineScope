package com.malabar.malabarmoviesapp.domain.repository

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.details.MovieDetailsResponse
import com.malabar.malabarmoviesapp.domain.data.images.MovieImageResponse
import com.malabar.malabarmoviesapp.domain.data.video.MovieVideoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieDetailsRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveMovieDetails(id: Int): Either<Failure, MovieDetailsResponse> {
        return Either.Right(movieApi.retrieveMovieDetails(id))
    }

    suspend fun retrieveMovieImages(movieId: Int): Flow<Either<Failure, MovieImageResponse>> =
        flow {
            try {
                emit(
                    Either.Right(
                        movieApi.retrieveMovieImages(movieId)
                    )
                )
            } catch (ex: Exception) {
                emit(
                    Either.Left(Failure.ServerError(ex.cause))
                )
            }

        }.flowOn(Dispatchers.IO)

    suspend fun retrieveMovieVideo(movieId: Int): Flow<Either<Failure, MovieVideoResponse>> =
        flow {
            try {
                emit(
                    Either.Right(
                        movieApi.retrieveMovieVideo(movieId)
                    )
                )
            } catch (ex: Exception) {
                emit(
                    Either.Left(Failure.ServerError(ex.cause))
                )
            }

        }.flowOn(Dispatchers.IO)
}