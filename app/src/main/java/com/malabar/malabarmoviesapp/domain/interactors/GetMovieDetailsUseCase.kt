package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.IdParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.UseCase
import com.malabar.malabarmoviesapp.domain.data.details.MovieDetailsResponse
import com.malabar.malabarmoviesapp.domain.repository.MovieDetailsRepository

class GetMovieDetailsUseCase(
    private val movieDetailsRepository: MovieDetailsRepository
) : UseCase<MovieDetailsResponse, IdParam>() {
    override suspend fun run(params: IdParam): Either<Failure, MovieDetailsResponse> {
        return movieDetailsRepository.retrieveMovieDetails(
            id = params.id
        )
    }
}