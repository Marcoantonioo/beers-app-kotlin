package com.example.beerappkoltin.domain.usecase

import com.example.beerappkoltin.core.commons.BaseUseCase
import com.example.beerappkoltin.core.commons.Params
import com.example.beerappkoltin.domain.model.BeerDomain
import com.example.beerappkoltin.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow

interface LoadAllPagedBeerUseCase : BaseUseCase<List<BeerDomain>, LoadAllBeerParams> {
    override suspend fun execute(params: LoadAllBeerParams): Flow<Result<List<BeerDomain>>>
}

class LoadAllPagedBeerUseCaseImpl(
    private val repository: BeerRepository,
) : LoadAllPagedBeerUseCase {
    override suspend fun execute(params: LoadAllBeerParams) =
        repository.loadAllPaged(params.page)
}

data class LoadAllBeerParams(
    val page: Int = 1
) : Params()
