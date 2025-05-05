package com.malabar.core.interactors

import arrow.core.Either
import com.malabar.core.failure.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(
        params: Params,
        scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        scope.launch {
            val deferreds = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferreds.await())
        }
    }
}