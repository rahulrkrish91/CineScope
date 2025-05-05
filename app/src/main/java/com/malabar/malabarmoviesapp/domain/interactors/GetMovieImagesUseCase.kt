package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.IdParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.images.MovieImageResponse
import com.malabar.malabarmoviesapp.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetMovieImagesUseCase(
    private val movieDetailsRepository: MovieDetailsRepository
) : FlowUseCase<MovieImageResponse, IdParam>() {
    override suspend fun run(params: IdParam): Flow<Either<Failure, MovieImageResponse>> {
        return movieDetailsRepository.retrieveMovieImages(movieId = params.id)
    }
}