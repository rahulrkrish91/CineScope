package com.malabar.malabarmoviesapp.domain.repository.tv

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.tv.season.TvSeasonDetailsResponse

class TvSeasonDetailsRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveSeasonDetails(
        seriesId: Int,
        seasonNumber: Int
    ): Either<Failure, TvSeasonDetailsResponse> {
        return try {
            Either.Right(movieApi.retrieveSeasonDetails(seriesId, seasonNumber))
        } catch (ex: Exception) {
            Either.Left(Failure.ServerError(ex.cause))
        }
    }
}