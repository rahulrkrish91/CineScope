package com.malabar.malabarmoviesapp.domain.repository

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.cast.MovieCastResponse
import com.malabar.malabarmoviesapp.domain.data.cast.details.MovieCastInfo

class MovieCastRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveMovieCredits(
        movieId: Int,
        language: String
    ): Either<Failure, MovieCastResponse> {
        return Either.Right(
            movieApi.retrieveMovieCredits(
                movieId = movieId,
                language = language
            )
        )
    }

    suspend fun retrieveMovieCastDetails(
        personId: Int
    ): Either<Failure, MovieCastInfo> {
        return Either.Right(
            movieApi.retrieveMovieCastDetails(
                personId = personId
            )
        )
    }
}