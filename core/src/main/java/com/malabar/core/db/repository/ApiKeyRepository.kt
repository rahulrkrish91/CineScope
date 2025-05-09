package com.malabar.core.db.repository

import arrow.core.Either
import com.malabar.core.db.dao.ApiKeyDao
import com.malabar.core.db.data.ApiKey
import com.malabar.core.failure.Failure

class ApiKeyRepository(
    private val apiKeyDao: ApiKeyDao
) {

    suspend fun insertKey(apiKey: ApiKey): Either<Failure, Unit> {
        return Either.Right(apiKeyDao.insertKey(apiKey))
    }

    suspend fun getApiKey(): Either<Failure, ApiKey> {
        return Either.Right(apiKeyDao.getApiKey())
    }

    suspend fun deleteApiKey(): Either<Failure, Unit> {
        return Either.Right(apiKeyDao.deleteApiKey())
    }
}