package com.example.beerappkoltin.presentation.ui.beerList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerappkoltin.domain.model.BeerDomain
import com.example.beerappkoltin.domain.usecase.LoadAllBeerParams
import com.example.beerappkoltin.domain.usecase.LoadAllPagedBeerUseCase
import com.example.beerappkoltin.presentation.model.BeerListEvent
import com.example.beerappkoltin.presentation.model.BeerListState
import com.example.beerappkoltin.presentation.model.BeerView
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BeerListViewModel(
    private val beerUseCase: LoadAllPagedBeerUseCase,
) : ViewModel() {

    private val _listState = MutableStateFlow(BeerListState())
    val listState: StateFlow<BeerListState>
        get() = _listState

    fun dispatchEvent(event: BeerListEvent) {
        when (event) {
            is BeerListEvent.LoadMore -> loadMore()
        }
    }

    private fun loadMore() {
        viewModelScope.launch {
            val nextPage = _listState.value.currentPage + PLUS_ONE;
            beerUseCase.execute(LoadAllBeerParams(page = nextPage))
                .onStart {
                    onLoading(true)
                }.onCompletion {
                    onLoading(false)
                }.collect {
                    it.fold(onFailure = ::onFailure, onSuccess = ::onSuccess)
                }
        }
    }

    private fun onLoading(isLoading: Boolean) {
        val isListEmpty = _listState.value.success.isEmpty()

        _listState.value = _listState.value.copy(isLoading = if (isListEmpty) isLoading else false)
        _listState.value =
            _listState.value.copy(isLoadingMore = if (!isListEmpty) isLoading else false)
    }

    private fun onFailure(error: Throwable) {
        _listState.value = _listState.value.copy(error = error.message ?: "Error")
    }

    private fun onSuccess(beers: List<BeerDomain>) {
        val oldList: List<BeerView> = _listState.value.success
        val newList: List<BeerView> = beers.map { it.toView() }
        val currentList = oldList + newList
        val currentPage = _listState.value.currentPage + PLUS_ONE;
        _listState.value = _listState.value.copy(success = currentList, currentPage = currentPage)
    }

    companion object {
        const val PLUS_ONE = 1
    }
}
