package com.example.beerappkoltin.data.repository

import com.example.beerappkoltin.core.commons.Result
import com.example.beerappkoltin.data.remote.service.BeerRetrofitService
import com.example.beerappkoltin.domain.repository.BeerRepository
import kotlinx.coroutines.flow.flow

class BeerRepositoryImpl(
    private val service: BeerRetrofitService
) : BeerRepository {
    override suspend fun loadAllPaged(page: Int) =
        flow {
            val res = service.loadAllPaged(page).map { it.toModel() }
            emit(Result.Success(res))
        }
}
