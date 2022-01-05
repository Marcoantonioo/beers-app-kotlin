package com.example.beerappkoltin.domain

import android.util.Log
import com.example.beerappkoltin.domain.usecase.LoadAllPagedBeerUseCase
import com.example.beerappkoltin.domain.usecase.LoadAllPagedBeerUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<LoadAllPagedBeerUseCase> { LoadAllPagedBeerUseCaseImpl(repository = get()) }
}