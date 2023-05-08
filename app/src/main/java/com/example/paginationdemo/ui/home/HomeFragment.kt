package com.example.paginationdemo.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.modelviewer.utils.viewbinding.viewBinding
import com.example.paginationdemo.R
import com.example.paginationdemo.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val homeViewModel by viewModels<HomeViewModel>()

    private val verticalPassengersAdapter = VerticalPassengersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            verticalPassengersRecyclerView.adapter = verticalPassengersAdapter
            swipeRefreshLayout.setOnRefreshListener {
                verticalPassengersAdapter.refresh()
                swipeRefreshLayout.isRefreshing = false
            }
        }
        getPassengers()
    }

    private fun getPassengers() {
        viewLifecycleOwner.lifecycleScope.launch {
            with(homeViewModel) {
                async {
                    getPassengers().collectLatest { verticalPassengersAdapter.submitData(it) }
                }
            }
        }
        verticalPassengersAdapter.addLoadStateListener { loadState ->
            val isLoading = loadState.refresh is LoadState.Loading
            val isError = loadState.refresh is LoadState.Error || loadState.append is LoadState.Error
            val endReached = loadState.append.endOfPaginationReached
            binding.loadingBar.isVisible = isLoading
            if (isError) handleError()
            if (endReached) handleEndOfPaginationReached()
        }
    }

    private fun handleError() {
        Snackbar.make(binding.root, "Unknown error", Snackbar.LENGTH_LONG).show()
    }

    private fun handleEndOfPaginationReached() {
        Snackbar.make(binding.root, "End is reached", Snackbar.LENGTH_LONG).show()
    }
}
