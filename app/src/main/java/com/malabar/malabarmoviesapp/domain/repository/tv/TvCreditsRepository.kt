package com.malabar.malabarmoviesapp.domain.repository.tv

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.cast.MovieCastResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TvCreditsRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveTvCredits(seriesId: Int): Flow<Either<Failure, MovieCastResponse>> = flow {
        try {
            emit(
                Either.Right(movieApi.retrieveTvCredits(seriesId))
            )
        } catch (ex: Exception) {
            emit(
                Either.Left(Failure.ServerError(ex.cause))
            )
        }
    }.flowOn(Dispatchers.IO)
}