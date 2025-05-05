package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.RecommendedMovieParams
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.recommended.MovieRecommendedResponse
import com.malabar.malabarmoviesapp.domain.repository.MovieRecommendedRepository
import kotlinx.coroutines.flow.Flow

class GetSimilarMoviesUseCase(
    private val movieRecommendedRepository: MovieRecommendedRepository
) : FlowUseCase<MovieRecommendedResponse, RecommendedMovieParams>() {
    override suspend fun run(params: RecommendedMovieParams): Flow<Either<Failure, MovieRecommendedResponse>> {
        return movieRecommendedRepository.retrieveMovieSimilar(
            movieId = params.movie_id,
            language = params.language,
            page = params.page
        )
    }
}