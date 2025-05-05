package com.malabar.malabarmoviesapp.domain.repository

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.recommended.MovieRecommendedResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRecommendedRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveMovieRecommendations(
        movieId: Int,
        language: String,
        page: Int
    ): Flow<Either<Failure, MovieRecommendedResponse>> = flow {
        emit(
            Either.Right(
                movieApi.retrieveMovieRecommendations(
                    movieId,
                    language,
                    page
                )
            )
        )
    }.flowOn(Dispatchers.IO)

    suspend fun retrieveMovieSimilar(
        movieId: Int,
        language: String,
        page: Int
    ): Flow<Either<Failure, MovieRecommendedResponse>> = flow {
        emit(
            Either.Right(
                movieApi.retrieveMovieSimilar(
                    movieId,
                    language,
                    page
                )
            )
        )
    }.flowOn(Dispatchers.IO)
}