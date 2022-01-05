package com.example.beerappkoltin.data

import com.example.beerappkoltin.core.commons.Constants
import com.example.beerappkoltin.data.remote.service.BeerRetrofitService
import com.example.beerappkoltin.data.repository.BeerRepositoryImpl
import com.example.beerappkoltin.domain.repository.BeerRepository
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    factory<BeerRepository> { BeerRepositoryImpl(service = get()) }

    single { providerBeerRetrofitService(retrofit = get()) }

    single {
        provideRetrofit(
            okHttpClient = get(),
            url = Constants.BASE_URL
        )
    }

    single { provideOkHttpClient() }
}

internal fun providerBeerRetrofitService(retrofit: Retrofit): BeerRetrofitService =
    retrofit.create(BeerRetrofitService::class.java)


internal fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .serializeNulls()
        .create()

    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

internal fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .build()
}