package com.example.beerappkoltin.data.remote.service

import com.example.beerappkoltin.data.remote.entity.BeerRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerRetrofitService {
    @GET("beers")
    suspend fun loadAllPaged(
        @Query(value = "page") page: Int,
        @Query(value = "per_page") per_page: Int = PER_PAGE
    ): List<BeerRemote>

    companion object {
        const val PER_PAGE = 20
    }
}