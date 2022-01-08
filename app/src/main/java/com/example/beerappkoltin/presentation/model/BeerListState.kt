package com.example.beerappkoltin.presentation.model

data class BeerListState(
    var isLoading: Boolean = false,
    var error: String? = "",
    var success: List<BeerView> = emptyList(),
    var currentPage: Int = 0,
    var isLoadingMore: Boolean = false
)