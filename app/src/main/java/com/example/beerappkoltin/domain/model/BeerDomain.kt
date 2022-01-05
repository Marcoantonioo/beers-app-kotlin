package com.example.beerappkoltin.domain.model

import com.example.beerappkoltin.presentation.model.BeerView

data class BeerDomain(
    var id: Long?,
    var name: String?,
    var tagLine: String?,
    var firstBrewed: String?,
    var description: String?,
    var imageUrl: String?,
) {
    fun toView() = BeerView(
        id = this.id,
        name = this.name,
        tagLine = this.tagLine,
        firstBrewed = this.firstBrewed,
        description = this.description,
        imageUrl = this.imageUrl,
    )
}
