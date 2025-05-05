package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.MovieSearchParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.search.trending.TrendingPersonResult
import com.malabar.malabarmoviesapp.domain.repository.MovieTrendingRepository
import kotlinx.coroutines.flow.Flow

class GetTrendingPersonUseCase(
    private val movieTrendingRepository: MovieTrendingRepository
) : FlowUseCase<TrendingPersonResult, MovieSearchParam>() {
    override suspend fun run(params: MovieSearchParam): Flow<Either<Failure, TrendingPersonResult>> {
        return movieTrendingRepository.retrieveTrendingPersons(timeWindow = params.query)
    }
}