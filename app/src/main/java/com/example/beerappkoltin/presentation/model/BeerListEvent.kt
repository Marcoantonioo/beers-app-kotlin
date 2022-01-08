package com.example.beerappkoltin.presentation.model

sealed class BeerListEvent {
    object LoadMore : BeerListEvent()
}