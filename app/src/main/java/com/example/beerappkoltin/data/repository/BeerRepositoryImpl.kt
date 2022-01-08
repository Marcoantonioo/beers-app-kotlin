package com.example.beerappkoltin.data.repository

import com.example.beerappkoltin.data.remote.service.BeerRetrofitService
import com.example.beerappkoltin.domain.model.BeerDomain
import com.example.beerappkoltin.domain.repository.BeerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BeerRepositoryImpl(
    private val service: BeerRetrofitService
) : BeerRepository {
    override suspend fun loadAllPaged(page: Int) =
        flow<Result<List<BeerDomain>>> {
            try {
                // Só para demorar um pouquinho hehe
                delay(DELAY)
                val res = service.loadAllPaged(page).map { it.toModel() }
                emit(Result.success(res))
            } catch (ex: Exception) {
                emit(Result.failure(Exception("Não foi possível se comunicar com a API: ${ex.message}")))
            }
        }

    override suspend fun loadById(id: Long): Flow<Result<List<BeerDomain>>> {
        return flow<Result<List<BeerDomain>>>  {
            try {
                val res = service.loadById(id).map { it.toModel() }
                emit(Result.success(res))
            } catch (ex: Exception) {
                emit(Result.failure(Exception("Não foi possível se comunicar com a API: ${ex.message}")))
            }
        }
    }

    companion object {
        const val DELAY = 3000L
    }
}
