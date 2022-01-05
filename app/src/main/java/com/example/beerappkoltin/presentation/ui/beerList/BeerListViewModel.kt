package com.example.beerappkoltin.presentation.ui.beerList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerappkoltin.core.commons.Result
import com.example.beerappkoltin.domain.model.BeerDomain
import com.example.beerappkoltin.domain.usecase.LoadAllBeerParams
import com.example.beerappkoltin.domain.usecase.LoadAllPagedBeerUseCase
import com.example.beerappkoltin.presentation.model.BeerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class BeerListViewModel(
    private val beerUseCase: LoadAllPagedBeerUseCase,
) : ViewModel() {

    var currentPage = INITIAL_PAGE

    var liveData: MutableLiveData<Result<List<BeerView>>> = MutableLiveData()

    var mainList = mutableListOf<BeerView>()

    fun loadMore() {
        viewModelScope.launch {
            // para demorar um pouquinho hehe
            delay(DELAY)
            currentPage += PLUS_ONE
            beerUseCase.execute(LoadAllBeerParams(page = currentPage))
                .onStart {
                    emit(Result.Loading)
                }.map { result ->
                    onMapResultFlow(result)
                }.collect {
                    onSuccessCollect(it)
                }
        }
    }

    private fun onMapResultFlow(result: Result<List<BeerDomain>>): Result<List<BeerView>> {
        liveData.value?.mapResultSuccess { mainList.addAll(it) }

        return result.mapResultSuccess {
            it.map { item -> item.toView() }
        }
    }

    private fun onSuccessCollect(result: Result<List<BeerView>>) {
        liveData.postValue(result)
    }

    companion object {
        const val DELAY = 3000L
        const val INITIAL_PAGE = 0
        const val PLUS_ONE = 1
    }
}