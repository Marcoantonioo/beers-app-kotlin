package com.example.beerappkoltin.domain.repository

import com.example.beerappkoltin.core.commons.Result
import com.example.beerappkoltin.domain.model.BeerDomain
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun loadAllPaged(page: Int): Flow<Result<List<BeerDomain>>>
}
