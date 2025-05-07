package com.malabar.malabarmoviesapp.domain.repository.tv

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.tv.cast.credits.TvCastCreditsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class TvCastCreditsRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrievePersonTvCredits(personId: Int): Flow<Either<Failure, TvCastCreditsResponse>> {
        return flow {
            emit(
                Either.Right(
                    movieApi.retrievePersonTvCredits(personId)
                )
            )
        }.catch { ex ->
            Either.Right(
                Failure.ServerError(ex)
            )
        }
    }
}