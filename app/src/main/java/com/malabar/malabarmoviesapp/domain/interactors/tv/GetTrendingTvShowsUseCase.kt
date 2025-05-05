package com.malabar.malabarmoviesapp.domain.interactors.tv

import arrow.core.Either
import com.malabar.core.data.params.MovieSearchParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.tv.airing_today.AiringTodayResponse
import com.malabar.malabarmoviesapp.domain.repository.tv.TvTrendingRepository
import kotlinx.coroutines.flow.Flow

class GetTrendingTvShowsUseCase(
    private val tvTrendingRepository: TvTrendingRepository
) : FlowUseCase<AiringTodayResponse, MovieSearchParam>() {
    override suspend fun run(params: MovieSearchParam): Flow<Either<Failure, AiringTodayResponse>> {
        return tvTrendingRepository.retrieveTrendingTv(
            time_window = params.query
        )
    }
}