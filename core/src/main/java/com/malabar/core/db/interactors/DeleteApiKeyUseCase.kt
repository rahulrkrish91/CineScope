package com.malabar.core.db.interactors

import arrow.core.Either
import com.malabar.core.db.repository.ApiKeyRepository
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.UseCase

class DeleteApiKeyUseCase(
    private val apiKeyRepository: ApiKeyRepository
) : UseCase<Unit, Unit>() {
    override suspend fun run(params: Unit): Either<Failure, Unit> {
        return apiKeyRepository.deleteApiKey()
    }
}