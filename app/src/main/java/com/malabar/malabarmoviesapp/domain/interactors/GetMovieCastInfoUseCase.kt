package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.IdParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.UseCase
import com.malabar.malabarmoviesapp.domain.data.cast.details.MovieCastInfo
import com.malabar.malabarmoviesapp.domain.repository.MovieCastRepository

class GetMovieCastInfoUseCase(
    private val movieCastRepository: MovieCastRepository
) : UseCase<MovieCastInfo, IdParam>() {
    override suspend fun run(params: IdParam): Either<Failure, MovieCastInfo> {
        return movieCastRepository.retrieveMovieCastDetails(
            personId = params.id
        )
    }
}