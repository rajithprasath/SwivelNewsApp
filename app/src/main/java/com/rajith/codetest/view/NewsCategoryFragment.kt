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
import com.rajith.codetest.adapter.NewsCategoryAdapter
import com.rajith.codetest.databinding.FragmentNewsCategoryBinding
import com.rajith.codetest.di.DaggerNewsComponent
import com.rajith.codetest.model.News
import com.rajith.codetest.model.NewsCategory
import com.rajith.codetest.util.Origin
import com.rajith.codetest.util.UiState
import com.rajith.codetest.view.base.BaseFragment
import com.rajith.codetest.viewmodel.NewsCategoryViewModel
import javax.inject.Inject

class NewsCategoryFragment : BaseFragment<FragmentNewsCategoryBinding, NewsCategoryViewModel>(),
    NewsCategoryAdapter.NewsCategoryClickListener, NewsAdapter.NewsClickListener {

    private lateinit var adapter: NewsCategoryAdapter
    private var categories = mutableListOf<NewsCategory>()
    @Inject
    lateinit var factory: NewsCategoryViewModel.Factory
    private lateinit var newsAdapter: NewsAdapter
    private var selectedCategoryIndex = 0
    private var isUserSwitched = false

    override val vm: NewsCategoryViewModel by viewModels(factoryProducer = { factory })

    override fun getLayoutResourceId() = R.layout.fragment_news_category

    override fun initDaggerComponent() {
        DaggerNewsComponent
            .builder()
            .coreComponent(NewsApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }

    companion object {

        fun newInstance(): NewsCategoryFragment {
            return NewsCategoryFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoryRecyclerView()
    }


    private fun initCategoryRecyclerView() {
        adapter = NewsCategoryAdapter(this)
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.adapter = adapter
        categories.clear()
        adapter.submitList(populateCategories())
        setUpNewsLoading()
    }

    private fun setUpNewsLoading() {
        initRecyclerView()
        vm.setSearchQuery(null, categories[selectedCategoryIndex].name.toLowerCase())
        vm.news.observe(viewLifecycleOwner, Observer { subscribeNews(it) })
        vm.initialLoadingState.observe(
            viewLifecycleOwner,
            Observer { subscribeInitialLoadingState(it) })
        vm.searchState.observe(viewLifecycleOwner, Observer { subscribeNewsState(it) })
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter(this)
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.newsRecyclerView.layoutManager = layoutManager
        binding.newsRecyclerView.isNestedScrollingEnabled = false
        binding.newsRecyclerView.adapter = newsAdapter
    }

    private fun populateCategories(): List<NewsCategory> {

        categories.add(NewsCategory(0, "Bitcoin", false))
        categories.add(NewsCategory(1, "Apple", false))
        categories.add(NewsCategory(2, "Earth\nquake", false))
        categories.add(NewsCategory(3, "Animal", false))

        for (item in categories) {
            item.isSelected = item.id == selectedCategoryIndex
        }
        return categories
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
        newsAdapter.setState(it)
    }

    private fun subscribeNews(data: PagedList<News>) {
        if (data.size > 0) {
            newsAdapter.notifyDataSetChanged()
            newsAdapter.submitList(data)
            binding.isLoading = false
            if(isUserSwitched){
                binding.newsRecyclerView.scrollToPosition(0)
            }
            isUserSwitched = false
        }

    }

    override fun onNewsCategoryPressed(category: NewsCategory) {
        isUserSwitched = true
        selectedCategoryIndex = category.id
        for (item in categories) {
            item.isSelected = item == category
        }
        adapter.notifyDataSetChanged()

        vm.setSearchQuery(null, category.name.toLowerCase())
    }

    override fun onHeadlinePressed(headline: News) {
        isUserSwitched = false
        val action =
            NewsCategoryFragmentDirections.actionFragmentNewsCategoryToFragmentNewsDetails(
                headline,
                Origin.CATEGORY
            )
        findNavController().navigate(action)
    }


}