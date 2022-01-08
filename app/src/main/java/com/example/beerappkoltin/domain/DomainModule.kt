package com.example.beerappkoltin.domain

import com.example.beerappkoltin.domain.usecase.LoadAllPagedBeerUseCase
import com.example.beerappkoltin.domain.usecase.LoadAllPagedBeerUseCaseImpl
import com.example.beerappkoltin.domain.usecase.LoadByIdUseCase
import com.example.beerappkoltin.domain.usecase.LoadByIdUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<LoadAllPagedBeerUseCase> { LoadAllPagedBeerUseCaseImpl(repository = get()) }
    factory<LoadByIdUseCase> { LoadByIdUseCaseImpl(repository = get()) }
}