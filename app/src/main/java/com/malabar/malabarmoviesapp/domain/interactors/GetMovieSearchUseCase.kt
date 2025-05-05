package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.MovieSearchParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.search.MovieSearchResponse
import com.malabar.malabarmoviesapp.domain.repository.MovieSearchRepository
import kotlinx.coroutines.flow.Flow

class GetMovieSearchUseCase(
    private val movieSearchRepository: MovieSearchRepository
) : FlowUseCase<MovieSearchResponse, MovieSearchParam>() {
    override suspend fun run(params: MovieSearchParam): Flow<Either<Failure, MovieSearchResponse>> {
        return movieSearchRepository.retrieveMovieSearch(
            query = params.query
        )
    }
}