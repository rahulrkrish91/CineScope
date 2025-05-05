package com.malabar.malabarmoviesapp.domain.interactors.tv

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.tv.airing_today.AiringTodayResponse
import com.malabar.malabarmoviesapp.domain.repository.tv.TvOnTheAirRepository
import kotlinx.coroutines.flow.Flow

class GetTvOnTheAirUseCase(
    private val tvOnTheAirRepository: TvOnTheAirRepository
) : FlowUseCase<AiringTodayResponse, Unit>() {
    override suspend fun run(params: Unit): Flow<Either<Failure, AiringTodayResponse>> {
        return tvOnTheAirRepository.retrieveOnTheAir()
    }
}