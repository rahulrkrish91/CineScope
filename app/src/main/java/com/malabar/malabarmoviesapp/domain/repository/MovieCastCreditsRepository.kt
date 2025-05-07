package com.malabar.malabarmoviesapp.domain.repository

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.cast.credits.MovieCastCreditsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MovieCastCreditsRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrievePersonMovieCredits(
        personId: Int
    ): Flow<Either<Failure, MovieCastCreditsResponse>> {
        return flow {
            emit(Either.Right(movieApi.retrievePersonMovieCredits(personId)))
        }.catch { ex ->
            Either.Left(Failure.ServerError(ex))
        }
    }
}