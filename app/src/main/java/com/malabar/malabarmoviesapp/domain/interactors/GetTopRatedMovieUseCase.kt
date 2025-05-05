package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.MovieNowPlayingParams
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.MoviePopularResponse
import com.malabar.malabarmoviesapp.domain.repository.MovieTopRatedRepository
import kotlinx.coroutines.flow.Flow

class GetTopRatedMovieUseCase(
    private val movieTopRatedRepository: MovieTopRatedRepository
): FlowUseCase<MoviePopularResponse, MovieNowPlayingParams>() {
    override suspend fun run(params: MovieNowPlayingParams): Flow<Either<Failure, MoviePopularResponse>> {
        return movieTopRatedRepository.retrieveTopRatedMovies(
            language = params.language,
            page = params.page,
            region = params.region
        )
    }
}