package com.example.beerappkoltin.presentation.ui.beerList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.beerappkoltin.R
import com.example.beerappkoltin.core.commons.Result
import com.example.beerappkoltin.core.commons.handleObserver
import com.example.beerappkoltin.core.commons.setVisibilidadeVisibleView
import com.example.beerappkoltin.core.commons.visible
import com.example.beerappkoltin.databinding.FragmentBeerListBinding
import com.example.beerappkoltin.presentation.model.BeerView
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeerListFragment : Fragment() {
    private val mViewModel by viewModel<BeerListViewModel>()

    private lateinit var mBinding: FragmentBeerListBinding
    private lateinit var mAdapter: BeerListAdapter

    private var isLoadingMore = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_beer_list, container, false)

        configAdapter()

        configRecyclerView()

        mViewModel.liveData.handleObserver(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> configViewWhenIsSuccess(it.data)
                is Result.Loading -> configViewWhenIsLoading(isLoading = true)
                is Result.Error -> configViewWhenIsError(it.error)
            }
        }

        configButtonTryAgain()

        return mBinding.root
    }

    private fun configButtonTryAgain() {
        mBinding.btnTryAgain.setOnClickListener {
            isLoadingMore = false
            mViewModel.loadMore()
        }
    }

    private fun configViewWhenIsError(message: String) {
        configViewWhenIsLoading(false)
        mBinding.textViewError.text = message
        setVisibilidadeVisibleView(mBinding.textViewError, true)
        setVisibilidadeVisibleView(mBinding.btnTryAgain, true)
    }

    private fun configViewWhenIsSuccess(list: List<BeerView>) {
        configViewWhenIsLoading(false)
        setVisibilidadeVisibleView(mBinding.progressBarLoadMore, false)
        setVisibilidadeVisibleView(mBinding.textViewError, false)
        setVisibilidadeVisibleView(mBinding.btnTryAgain, false)
        notifyAdapterWithNewList(list)
    }

    private fun configViewWhenIsLoading(isLoading: Boolean) {
        setVisibilidadeVisibleView(mBinding.progressBar, isLoading && !isLoadingMore)
        setVisibilidadeVisibleView(mBinding.progressBarLoadMore, isLoadingMore)
        setVisibilidadeVisibleView(mBinding.textViewError, !isLoading)
        setVisibilidadeVisibleView(mBinding.btnTryAgain, !isLoading)
    }

    private fun notifyAdapterWithNewList(list: List<BeerView>) {
        mViewModel.mainList.addAll(list)
        mAdapter.list = mViewModel.mainList
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
                isLoadingMore = true
                mViewModel.loadMore()
            }
        }
    }

    private fun configAdapter() = context?.let { mAdapter = BeerListAdapter(it) }

    override fun onResume() {
        super.onResume()
        if (mViewModel.mainList.isEmpty()) {
            isLoadingMore = false
            mViewModel.loadMore()
        }
    }

    companion object {
        const val GRID_SPAN_COUNT = 2
        const val DIRECTION = 1
    }
}