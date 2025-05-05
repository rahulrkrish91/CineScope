package com.malabar.malabarmoviesapp.domain.interactors.tv

import arrow.core.Either
import com.malabar.core.data.params.IdParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.cast.MovieCastResponse
import com.malabar.malabarmoviesapp.domain.repository.tv.TvCreditsRepository
import kotlinx.coroutines.flow.Flow

class GetTvCreditsUseCase(
    private val tvCreditsRepository: TvCreditsRepository
) : FlowUseCase<MovieCastResponse, IdParam>() {
    override suspend fun run(params: IdParam): Flow<Either<Failure, MovieCastResponse>> {
        return tvCreditsRepository.retrieveTvCredits(seriesId = params.id)
    }
}