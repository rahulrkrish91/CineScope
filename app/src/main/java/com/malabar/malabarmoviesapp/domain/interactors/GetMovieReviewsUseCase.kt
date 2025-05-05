package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.IdParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.UseCase
import com.malabar.malabarmoviesapp.domain.data.reviews.MovieReviewResponse
import com.malabar.malabarmoviesapp.domain.repository.MovieReviewRepository

class GetMovieReviewsUseCase(
    private val movieReviewRepository: MovieReviewRepository
) : UseCase<MovieReviewResponse, IdParam>() {
    override suspend fun run(params: IdParam): Either<Failure, MovieReviewResponse> {
        return movieReviewRepository.retrieveMovieReview(
            movieId = params.id
        )
    }
}