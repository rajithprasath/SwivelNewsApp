package com.rajith.codetest.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajith.codetest.NewsApp
import com.rajith.codetest.R
import com.rajith.codetest.adapter.NewsAdapter
import com.rajith.codetest.databinding.FragmentTopHeadlineBinding
import com.rajith.codetest.di.DaggerNewsComponent
import com.rajith.codetest.model.News
import com.rajith.codetest.util.Origin
import com.rajith.codetest.util.UiState
import com.rajith.codetest.view.base.BaseFragment
import com.rajith.codetest.viewmodel.TopHeadlineViewModel
import javax.inject.Inject

class TopHeadlineFragment : BaseFragment<FragmentTopHeadlineBinding, TopHeadlineViewModel>(),
    NewsAdapter.NewsClickListener {

    @Inject
    lateinit var factory: TopHeadlineViewModel.Factory
    private lateinit var adapter: NewsAdapter
    override val vm: TopHeadlineViewModel by viewModels(factoryProducer = { factory })

    override fun getLayoutResourceId() = R.layout.fragment_top_headline

    override fun initDaggerComponent() {
        DaggerNewsComponent
            .builder()
            .coreComponent(NewsApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }

    companion object {

        fun newInstance(): TopHeadlineFragment {
            return TopHeadlineFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        vm.setSearchQuery("id", null)

        vm.news.observe(viewLifecycleOwner, Observer { subscribeNews(it) })
        vm.initialLoadingState.observe(
            viewLifecycleOwner,
            Observer { subscribeInitialLoadingState(it) })
        vm.searchState.observe(viewLifecycleOwner, Observer { subscribeNewsState(it) })
    }

    private fun initRecyclerView() {
        adapter = NewsAdapter(this)
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.adapter = adapter
    }

    private fun subscribeInitialLoadingState(it: UiState) {
        when (it) {
            is UiState.Loading -> {
                binding.isLoading = true
            }
            is UiState.Complete -> {
                binding.isLoading = false
            }
        }
    }


    private fun subscribeNewsState(it: UiState) {
        adapter.setState(it)
    }

    private fun subscribeNews(data: PagedList<News>) {
        adapter.submitList(data)
        binding.isLoading = false
    }


    override fun onHeadlinePressed(headline: News) {

        val action =
            TopHeadlineFragmentDirections.actionFragmentTopHeadLineToFragmentNewsDetails(
                headline,
                Origin.NEWS
            )
        findNavController().navigate(action)
    }
}