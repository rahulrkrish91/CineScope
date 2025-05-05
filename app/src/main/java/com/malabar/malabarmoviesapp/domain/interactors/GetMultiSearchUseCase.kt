package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.MovieSearchParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.UseCase
import com.malabar.malabarmoviesapp.domain.data.search.multi.result.MultiSearchResult
import com.malabar.malabarmoviesapp.domain.repository.MovieSearchRepository

class GetMultiSearchUseCase(
    private val movieSearchRepository: MovieSearchRepository
) : UseCase<MutableList<MultiSearchResult>, MovieSearchParam>() {
    override suspend fun run(params: MovieSearchParam): Either<Failure, MutableList<MultiSearchResult>> {
        return movieSearchRepository.retrieveMultiSearch(
            query = params.query
        )
    }
}