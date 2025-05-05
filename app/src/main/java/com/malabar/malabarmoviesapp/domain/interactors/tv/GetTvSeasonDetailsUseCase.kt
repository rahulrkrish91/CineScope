package com.malabar.malabarmoviesapp.domain.interactors.tv

import arrow.core.Either
import com.malabar.core.data.params.TvSeasonParams
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.UseCase
import com.malabar.malabarmoviesapp.domain.data.tv.season.TvSeasonDetailsResponse
import com.malabar.malabarmoviesapp.domain.repository.tv.TvSeasonDetailsRepository

class GetTvSeasonDetailsUseCase(
    private val tvSeasonDetailsRepository: TvSeasonDetailsRepository
): UseCase<TvSeasonDetailsResponse, TvSeasonParams>() {
    override suspend fun run(params: TvSeasonParams): Either<Failure, TvSeasonDetailsResponse> {
        return tvSeasonDetailsRepository.retrieveSeasonDetails(
            seriesId = params.seriesId,
            seasonNumber = params.seasonNumber
        )
    }
}