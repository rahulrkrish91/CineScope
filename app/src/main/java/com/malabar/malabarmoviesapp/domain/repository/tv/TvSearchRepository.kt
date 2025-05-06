package com.malabar.malabarmoviesapp.domain.repository.tv

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.tv.search.TvSearchResponse

class TvSearchRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveTvSearchResult(query: String): Either<Failure, TvSearchResponse> {
        return try {
            Either.Right(
                movieApi.retrieveTvSearchResult(query)
            )
        } catch (ex: Exception) {
            Either.Left(
                Failure.ServerError(ex.cause)
            )
        }
    }
}