package com.malabar.malabarmoviesapp.domain.interactors.tv

import arrow.core.Either
import com.malabar.core.data.params.TvEpisodeParams
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.UseCase
import com.malabar.malabarmoviesapp.domain.data.tv.episode.EpisodeDetails
import com.malabar.malabarmoviesapp.domain.repository.tv.TvEpisodeDetailsRepository

class GetTvEpisodeDetailsUseCase(
    private val tvEpisodeRepository: TvEpisodeDetailsRepository
) : UseCase<EpisodeDetails, TvEpisodeParams>() {
    override suspend fun run(params: TvEpisodeParams): Either<Failure, EpisodeDetails> {
        return tvEpisodeRepository.getTvEpisodeDetails(
            series_id = params.seriesId,
            seasonNumber = params.seasonNumber,
            episodeNumber = params.episodeNumber
        )
    }
}