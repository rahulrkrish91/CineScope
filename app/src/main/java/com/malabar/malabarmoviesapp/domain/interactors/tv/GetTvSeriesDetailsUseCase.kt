package com.malabar.malabarmoviesapp.domain.interactors.tv

import arrow.core.Either
import com.malabar.core.data.params.IdParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.UseCase
import com.malabar.malabarmoviesapp.domain.data.tv.details.TvShowDetailsResponse
import com.malabar.malabarmoviesapp.domain.repository.tv.TvSeriesDetailsRepository

class GetTvSeriesDetailsUseCase(
    private val tvSeriesDetailsRepository: TvSeriesDetailsRepository
) : UseCase<TvShowDetailsResponse, IdParam>() {
    override suspend fun run(params: IdParam): Either<Failure, TvShowDetailsResponse> {
        return tvSeriesDetailsRepository.retrieveTvSeriesDetails(
            seriesID = params.id
        )
    }
}