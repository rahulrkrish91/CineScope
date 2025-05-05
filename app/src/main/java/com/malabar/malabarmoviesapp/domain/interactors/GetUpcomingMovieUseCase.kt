package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.MovieNowPlayingParams
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.MovieNowPlayingResponse
import com.malabar.malabarmoviesapp.domain.repository.MovieUpcomingRepository
import kotlinx.coroutines.flow.Flow

class GetUpcomingMovieUseCase(
    private val movieUpcomingRepository: MovieUpcomingRepository
) : FlowUseCase<MovieNowPlayingResponse, MovieNowPlayingParams>() {
    override suspend fun run(params: MovieNowPlayingParams): Flow<Either<Failure, MovieNowPlayingResponse>> {
        return movieUpcomingRepository.retrieveUpcomingMovies(
            language = params.language,
            page = params.page,
            region = params.region
        )
    }
}