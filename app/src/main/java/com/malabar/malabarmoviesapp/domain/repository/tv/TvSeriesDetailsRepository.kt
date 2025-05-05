package com.malabar.malabarmoviesapp.domain.repository.tv

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.tv.details.TvShowDetailsResponse

class TvSeriesDetailsRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveTvSeriesDetails(seriesID: Int): Either<Failure, TvShowDetailsResponse> {
        return try {
            Either.Right(movieApi.retrieveTvSeriesDetails(seriesID))
        } catch (ex: Exception) {
            Either.Left(Failure.ServerError(ex.cause))
        }
    }
}