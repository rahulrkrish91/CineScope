package com.malabar.core.db.interactors

import arrow.core.Either
import com.malabar.core.db.data.ApiKey
import com.malabar.core.db.repository.ApiKeyRepository
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.UseCase

class GetApiKeyUseCase(
    private val apiKeyRepository: ApiKeyRepository
) : UseCase<ApiKey, Unit>() {
    override suspend fun run(params: Unit): Either<Failure, ApiKey> {
        return apiKeyRepository.getApiKey()
    }

}