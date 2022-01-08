package com.example.beerappkoltin.data.remote.entity

import com.example.beerappkoltin.domain.model.BeerDomain

data class BeerRemote(
    var id: Long?,
    var name: String?,
    var tagLine: String?,
    var firstBrewed: String?,
    var description: String?,
    var imageUrl: String?,
    var attenuation_level: Double?
) {
    fun toModel() = BeerDomain(
        id = this.id,
        name = this.name,
        tagLine = this.tagLine,
        firstBrewed = this.firstBrewed,
        description = this.description,
        imageUrl = this.imageUrl,
        attenuation_level = this.attenuation_level,
    )
}