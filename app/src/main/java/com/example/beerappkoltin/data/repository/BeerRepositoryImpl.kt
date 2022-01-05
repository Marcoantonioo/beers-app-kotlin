package com.example.beerappkoltin.data.repository

import com.example.beerappkoltin.core.commons.Result
import com.example.beerappkoltin.data.remote.service.BeerRetrofitService
import com.example.beerappkoltin.domain.repository.BeerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class BeerRepositoryImpl(
    private val service: BeerRetrofitService
) : BeerRepository {
    override suspend fun loadAllPaged(page: Int) =
        flow {
            try {
                // Só para demorar um pouquinho hehe
                delay(DELAY)
                val res = service.loadAllPaged(page).map { it.toModel() }
                emit(Result.Success(res))
            } catch (ex: Exception) {
                emit(Result.Error("Não foi possível se comunicar com a API: ${ex.message}"))
            }
        }

    companion object {
        const val DELAY = 3000L
    }
}
