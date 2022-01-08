package com.example.beerappkoltin.presentation.model

data class BeerView(
    var id: Long?,
    var name: String?,
    var tagLine: String?,
    var firstBrewed: String?,
    var description: String?,
    var imageUrl: String?,
    var attenuation_level: Double?,
)
