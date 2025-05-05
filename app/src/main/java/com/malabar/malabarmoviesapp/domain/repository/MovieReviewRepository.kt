package com.malabar.malabarmoviesapp.domain.repository

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.reviews.MovieReviewResponse

class MovieReviewRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveMovieReview(movieId: Int): Either<Failure, MovieReviewResponse> {
        return try {
            Either.Right(
                movieApi.retrieveMovieReview(movieId)
            )
        } catch (ex: Exception) {
            Either.Left(
                Failure.ServerError(ex.cause)
            )
        }
    }
}