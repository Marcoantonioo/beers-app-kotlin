package com.example.beerappkoltin.presentation.ui.beerList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.beerappkoltin.R
import com.example.beerappkoltin.core.commons.setVisibilidadeVisibleView
import com.example.beerappkoltin.databinding.FragmentBeerListBinding
import com.example.beerappkoltin.presentation.model.BeerListEvent
import com.example.beerappkoltin.presentation.model.BeerListState
import com.example.beerappkoltin.presentation.model.BeerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeerListFragment : Fragment() {
    private val mViewModel by viewModel<BeerListViewModel>()

    private lateinit var mBinding: FragmentBeerListBinding
    private lateinit var mAdapter: BeerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_beer_list, container, false)

        configAdapter()

        configRecyclerView()

        lifecycleScope.launch {
            mViewModel.listState.collect { state ->
                onStateLoading(state)
                onStateError(state)
                onStateSuccess(state)
                onStateLoadMore(state)
            }
        }

        configButtonTryAgain()

        return mBinding.root
    }

    private fun configButtonTryAgain() {
        mBinding.btnTryAgain.setOnClickListener {
            dispatchLoadMore()
        }
    }

    private fun dispatchLoadMore() {
        mViewModel.dispatchEvent(BeerListEvent.LoadMore)
    }

    private fun onStateLoadMore(state: BeerListState) {
        setVisibilidadeVisibleView(mBinding.progressBarLoadMore, state.isLoadingMore)
    }

    private fun onStateSuccess(state: BeerListState) {
        setVisibilidadeVisibleView(mBinding.textViewError, false)
        setVisibilidadeVisibleView(mBinding.btnTryAgain, false)
        notifyAdapterWithNewList(state.success)
    }

    private fun onStateError(state: BeerListState) {
        state.error?.run {
            mBinding.textViewError.text = this
            setVisibilidadeVisibleView(mBinding.textViewError, true)
            setVisibilidadeVisibleView(mBinding.btnTryAgain, true)
        }
    }

    private fun onStateLoading(state: BeerListState) {
        setVisibilidadeVisibleView(mBinding.progressBar, state.isLoading)
        setVisibilidadeVisibleView(mBinding.textViewError, !state.isLoading)
        setVisibilidadeVisibleView(mBinding.btnTryAgain, !state.isLoading)
    }

    private fun notifyAdapterWithNewList(list: List<BeerView>) {
        mAdapter.list = list
        mAdapter.notifyDataSetChanged()
    }

    private fun configRecyclerView() {
        mBinding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(GRID_SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
        mBinding.recyclerView.adapter = mAdapter

        mBinding.recyclerView.addOnScrollListener(onScrollHitBottomLoadMore)

    }

    private val onScrollHitBottomLoadMore = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(DIRECTION) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                dispatchLoadMore()
            }
        }
    }

    private fun configAdapter() = context?.let { mAdapter = BeerListAdapter(it) }

    override fun onResume() {
        super.onResume()
        if (mViewModel.listState.value.success.isEmpty()) {
            dispatchLoadMore()
        }
    }

    companion object {
        const val GRID_SPAN_COUNT = 2
        const val DIRECTION = 1
    }
}