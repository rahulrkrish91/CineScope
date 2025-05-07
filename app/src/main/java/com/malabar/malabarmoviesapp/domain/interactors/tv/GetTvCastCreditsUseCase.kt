package com.malabar.malabarmoviesapp.domain.interactors.tv

import arrow.core.Either
import com.malabar.core.data.params.IdParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.tv.cast.credits.TvCastCreditsResponse
import com.malabar.malabarmoviesapp.domain.repository.tv.TvCastCreditsRepository
import kotlinx.coroutines.flow.Flow

class GetTvCastCreditsUseCase(
    private val tvCastCreditsRepository: TvCastCreditsRepository
) : FlowUseCase<TvCastCreditsResponse, IdParam>() {
    override suspend fun run(params: IdParam): Flow<Either<Failure, TvCastCreditsResponse>> {
        return tvCastCreditsRepository.retrievePersonTvCredits(
            personId = params.id
        )
    }
}