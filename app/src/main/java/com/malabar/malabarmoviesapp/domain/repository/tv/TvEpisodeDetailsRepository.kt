package com.malabar.malabarmoviesapp.domain.repository.tv

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.tv.episode.EpisodeDetails

class TvEpisodeDetailsRepository(
    private val movieApi: MovieApi
) {
    suspend fun getTvEpisodeDetails(
        series_id: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): Either<Failure, EpisodeDetails> {
        return try {
            Either.Right(movieApi.retrieveEpisodeDetails(series_id, seasonNumber, episodeNumber))
        } catch (ex: Exception) {
            Either.Left(Failure.ServerError(ex.cause))
        }
    }
}