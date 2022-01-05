package com.example.beerappkoltin.presentation.ui.beerList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.beerappkoltin.R
import com.example.beerappkoltin.core.commons.*
import com.example.beerappkoltin.databinding.FragmentBeerListBinding
import com.example.beerappkoltin.presentation.model.BeerView
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

        mViewModel.liveData.handleObserver(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> configViewWhenSuccess(it.data)
                is Result.Loading -> configViewWhenIsLoading(isLoading = true)
                is Result.Error -> Log.d("Passou Aqui", it.error)
            }
        }

        return mBinding.root
    }

    private fun configViewWhenSuccess(list: List<BeerView>) {
        configViewWhenIsLoading(false)
        mBinding.progressBarLoadMore.gone()
        notifyAdapterWithNewList(list)
    }

    private fun configViewWhenIsLoading(isLoading: Boolean) {
        setVisibilidadeVisibleView(mBinding.progressBar, isLoading)
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
            if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                mBinding.progressBarLoadMore.visible()
                mViewModel.loadMore()
            }
        }
    }

    private fun configAdapter() = context?.let { mAdapter = BeerListAdapter(it) }

    override fun onResume() {
        super.onResume()

        mViewModel.loadMore()
    }

    companion object {
        const val GRID_SPAN_COUNT = 2
    }
}