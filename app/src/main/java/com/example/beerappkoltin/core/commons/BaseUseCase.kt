package com.example.beerappkoltin.core.commons

import kotlinx.coroutines.flow.Flow
import kotlin.Result

interface BaseUseCase<TYPE, PARAMS : Params> {
    suspend fun execute(params: PARAMS): Flow<Result<TYPE>>
}

abstract class Params