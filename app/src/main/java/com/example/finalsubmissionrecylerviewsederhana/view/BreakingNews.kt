package com.example.finalsubmissionrecylerviewsederhana.view

import android.os.Bundle
import android.text.BoringLayout.make
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalsubmissionrecylerviewsederhana.MainActivity
import com.example.finalsubmissionrecylerviewsederhana.R
import com.example.finalsubmissionrecylerviewsederhana.adapter.ArticleAdapter
import com.example.finalsubmissionrecylerviewsederhana.databinding.FragmentBreakingNewsBinding
import com.example.finalsubmissionrecylerviewsederhana.utils.Resource
import com.example.finalsubmissionrecylerviewsederhana.utils.shareNews
import com.example.finalsubmissionrecylerviewsederhana.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class BreakingNews : Fragment(R.layout.fragment_breaking_news) {
    lateinit var viewModel1 : NewsViewModel
    lateinit var newsAdapter: ArticleAdapter
    val TAG = "FragmentNews"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel1 = (context as MainActivity).viewModel


        setupRecyclerView()
        setViewModelObserver()


    }
    private fun setupRecyclerView(){
        newsAdapter = ArticleAdapter()
        rycv_dashboard.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        newsAdapter.setOnclickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.actiong_breakingNewsFragment_toArticlFragment,bundle)

        }
        newsAdapter.setSaveListener {
//            if (it.id == null){
//                it.id = Random.nextInt(0,1000)
//            }
                viewModel1.insertArticle(it)
                Toast.makeText(context,"Berhasil menyimpan", Toast.LENGTH_SHORT).show()
        }
        newsAdapter.setShareListener {
            shareNews(context,it)
        }
        newsAdapter.setDeleteListener {
            viewModel1.deleteArticle(it)
            Snackbar.make(requireView(),"Berhasil Menghapus", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setViewModelObserver(){
        viewModel1.breakingNews.observe(viewLifecycleOwner, Observer { newsResponse ->
            when(newsResponse){
                is Resource.Succes-> {
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
                    newsResponse.data?.let { news ->
                        rycv_dashboard.visibility = View.VISIBLE
                        newsAdapter.differ.submitList(news.articles)
                    }
                }
                is Resource.Error -> {
                    shimmerFrameLayout.visibility = View.GONE
                    newsResponse.message?.let { message ->
                        Log.e(TAG,"Error: $message")

                    }
                }
                is Resource.Loading ->{
                    shimmerFrameLayout.startShimmerAnimation()
                    shimmerFrameLayout.visibility = View.VISIBLE
                }
            }
        })
    }

}