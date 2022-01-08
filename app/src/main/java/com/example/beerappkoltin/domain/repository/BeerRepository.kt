package com.example.beerappkoltin.domain.repository

import com.example.beerappkoltin.domain.model.BeerDomain
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun loadAllPaged(page: Int): Flow<Result<List<BeerDomain>>>
    suspend fun loadById(id: Long): Flow<Result<List<BeerDomain>>>
}
