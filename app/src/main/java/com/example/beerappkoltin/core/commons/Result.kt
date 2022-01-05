package com.example.beerappkoltin.core.commons

sealed class Result<out R> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()

    fun <ToMap> mapResultSuccess(getData: (R) -> ToMap): Result<ToMap> {
        return when (this) {
            is Success -> {
                Success(getData(data))
            }
            is Loading -> {
                this
            }
            is Error -> {
                this
            }
        }
    }
}
