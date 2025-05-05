package com.malabar.malabarmoviesapp.domain.interactors.tv

import arrow.core.Either
import com.malabar.core.data.params.TvSeasonParams
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.video.MovieVideoResponse
import com.malabar.malabarmoviesapp.domain.repository.tv.TvSeriesVideoRepository
import kotlinx.coroutines.flow.Flow

class GetTvSeasonVideoUseCase(
    private val tvSeriesVideoRepository: TvSeriesVideoRepository
) : FlowUseCase<MovieVideoResponse, TvSeasonParams>() {
    override suspend fun run(params: TvSeasonParams): Flow<Either<Failure, MovieVideoResponse>> {
        return tvSeriesVideoRepository.retrieveTvSeasonVideo(
            seriesId = params.seriesId,
            seasonNumber = params.seasonNumber
        )
    }
}