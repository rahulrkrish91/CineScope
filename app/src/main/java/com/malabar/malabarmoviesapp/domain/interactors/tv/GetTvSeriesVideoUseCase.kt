package com.malabar.malabarmoviesapp.domain.interactors.tv

import arrow.core.Either
import com.malabar.core.data.params.IdParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.video.MovieVideoResponse
import com.malabar.malabarmoviesapp.domain.repository.tv.TvSeriesVideoRepository
import kotlinx.coroutines.flow.Flow

class GetTvSeriesVideoUseCase(
    private val tvSeriesVideoRepository: TvSeriesVideoRepository
) : FlowUseCase<MovieVideoResponse, IdParam>() {
    override suspend fun run(params: IdParam): Flow<Either<Failure, MovieVideoResponse>> {
        return tvSeriesVideoRepository.retrieveTvVideo(seriesId = params.id)
    }
}