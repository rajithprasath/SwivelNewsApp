package com.rajith.codetest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.rajith.codetest.R
import com.rajith.codetest.databinding.FragmentNewsDetailBinding
import com.rajith.codetest.model.News

class NewsDetailFragment : Fragment() {

    private lateinit var binding : FragmentNewsDetailBinding
    private val args : NewsDetailFragmentArgs by navArgs()
    companion object {

        fun newInstance(): NewsDetailFragment {
            return NewsDetailFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_detail, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val news: News = args.news
        val origin = args.origin

        binding.news = news

    }


}