package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.MovieSearchParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.MoviePopularResponse
import com.malabar.malabarmoviesapp.domain.repository.MovieTrendingRepository
import kotlinx.coroutines.flow.Flow

class GetTrendingMoviesUseCase(
    private val movieTrendingRepository: MovieTrendingRepository
) : FlowUseCase<MoviePopularResponse, MovieSearchParam>() {
    override suspend fun run(params: MovieSearchParam): Flow<Either<Failure, MoviePopularResponse>> {
        return movieTrendingRepository.retrieveTrendingMovies(
            timeWindow = params.query
        )
    }
}