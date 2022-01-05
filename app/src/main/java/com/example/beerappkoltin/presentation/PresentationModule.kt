package com.example.beerappkoltin.presentation

import com.example.beerappkoltin.presentation.ui.beerList.BeerListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        BeerListViewModel(beerUseCase = get())
    }
}