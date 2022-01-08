package com.example.beerappkoltin.domain.usecase

import com.example.beerappkoltin.core.commons.BaseUseCase
import com.example.beerappkoltin.core.commons.Params
import com.example.beerappkoltin.core.commons.Result
import com.example.beerappkoltin.domain.model.BeerDomain
import com.example.beerappkoltin.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow

interface LoadByIdUseCase : BaseUseCase<List<BeerDomain>, LoadByIdParams> {
    override suspend fun execute(params: LoadByIdParams): Flow<Result<List<BeerDomain>>>
}

class LoadByIdUseCaseImpl(
    private val repository: BeerRepository,
) : LoadByIdUseCase {
    override suspend fun execute(params: LoadByIdParams) =
        repository.loadById(params.id)
}

data class LoadByIdParams(
    val id: Long
) : Params()