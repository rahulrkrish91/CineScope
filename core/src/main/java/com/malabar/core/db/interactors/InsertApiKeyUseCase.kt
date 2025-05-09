package com.malabar.core.db.interactors

import arrow.core.Either
import com.malabar.core.db.data.ApiKey
import com.malabar.core.db.repository.ApiKeyRepository
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.UseCase

class InsertApiKeyUseCase(
    private val apiKeyRepository: ApiKeyRepository
) : UseCase<Unit, ApiKey>() {
    override suspend fun run(params: ApiKey): Either<Failure, Unit> {
        return apiKeyRepository.insertKey(params)
    }
}