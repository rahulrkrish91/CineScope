package com.malabar.malabarmoviesapp.domain.interactors.tv

import arrow.core.Either
import com.malabar.core.data.params.TvEpisodeParams
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.video.MovieVideoResponse
import com.malabar.malabarmoviesapp.domain.repository.tv.TvSeriesVideoRepository
import kotlinx.coroutines.flow.Flow

class GetTvEpisodeVideoUseCase(
    private val tvSeriesVideoRepository: TvSeriesVideoRepository
) : FlowUseCase<MovieVideoResponse, TvEpisodeParams>() {
    override suspend fun run(params: TvEpisodeParams): Flow<Either<Failure, MovieVideoResponse>> {
        return tvSeriesVideoRepository.retrieveTvEpisodeVideos(
            seriesId = params.seriesId,
            seasonNumber = params.seasonNumber,
            episodeNumber = params.episodeNumber
        )
    }
}