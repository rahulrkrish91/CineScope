package com.malabar.malabarmoviesapp.domain.interactors.tv

import arrow.core.Either
import com.malabar.core.data.params.IdParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.tv.airing_today.AiringTodayResponse
import com.malabar.malabarmoviesapp.domain.repository.auth.TvRecommendationsRepository
import kotlinx.coroutines.flow.Flow

class GetTvRecommendationsUseCase(
    private val tvRecommendationsRepository: TvRecommendationsRepository
) : FlowUseCase<AiringTodayResponse, IdParam>() {
    override suspend fun run(params: IdParam): Flow<Either<Failure, AiringTodayResponse>> {
        return tvRecommendationsRepository.retrieveRecommendedTvShows(
            seriesID = params.id
        )
    }
}