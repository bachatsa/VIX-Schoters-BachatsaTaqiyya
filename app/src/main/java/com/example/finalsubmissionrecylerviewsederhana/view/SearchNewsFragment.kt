package com.example.finalsubmissionrecylerviewsederhana.view

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalsubmissionrecylerviewsederhana.MainActivity
import com.example.finalsubmissionrecylerviewsederhana.R
import com.example.finalsubmissionrecylerviewsederhana.adapter.ArticleAdapter
import com.example.finalsubmissionrecylerviewsederhana.adapter.SavedAdapter
import com.example.finalsubmissionrecylerviewsederhana.model.Article
import com.example.finalsubmissionrecylerviewsederhana.utils.Constants
import com.example.finalsubmissionrecylerviewsederhana.utils.Resource
import com.example.finalsubmissionrecylerviewsederhana.utils.shareNews
import com.example.finalsubmissionrecylerviewsederhana.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search) {
    lateinit var viewModel : NewsViewModel
    lateinit var newsAdapter: ArticleAdapter
    val TAG = "SearchFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setuRecyclerView()
        newsAdapter = ArticleAdapter()
        rycv_Search.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        newsAdapter.setOnclickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.actiong_searchNewsFragment_toArticlFragment,bundle)

        }
        newsAdapter.setSaveListener {
            viewModel.insertArticle(it)
            Toast.makeText(context,"Berhasil menyimpan", Toast.LENGTH_SHORT).show()
        }
        newsAdapter.setShareListener {
            shareNews(context,it)
        }
        newsAdapter.setDeleteListener {
            viewModel.deleteArticle(it)
            Snackbar.make(requireView(),"Berhasil Menghapus", Snackbar.LENGTH_SHORT).show()
        }
        var searchJob : Job? = null
        edtSearch.text.toString().trim()
        edtSearch.addTextChangedListener {  editable ->
            searchJob?.cancel()
            searchJob = MainScope().launch {
                delay(Constants.SEARCH_TIME_DELAY)
                editable?.let {
                    if (!editable.toString().trim().isEmpty()){
                        viewModel.getSearchNews(editable.toString())
                    }
                }
            }
        }
        viewModel.searchNews.observe( viewLifecycleOwner, Observer { newsResponse ->
            when(newsResponse){
                is Resource.Succes -> {
                    shimmer3FrameLayout.stopShimmerAnimation()
                    shimmer3FrameLayout.visibility = View.GONE
                    newsResponse.data?.let { news ->
                        newsAdapter.differ.submitList(news.articles)
                    }
                }
                is Resource.Error -> {
                    shimmerFrameLayout.visibility = View.VISIBLE
                    newsResponse.message?.let { message ->
                        Log.e(TAG,"Error: $message")

                    }
                }
                is Resource.Loading ->{
                    shimmer3FrameLayout.startShimmerAnimation()
                    shimmer3FrameLayout.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setuRecyclerView() {
        newsAdapter = ArticleAdapter()
        rycv_Search.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}