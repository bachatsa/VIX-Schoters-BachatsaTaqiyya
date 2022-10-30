package com.example.finalsubmissionrecylerviewsederhana.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalsubmissionrecylerviewsederhana.MainActivity
import com.example.finalsubmissionrecylerviewsederhana.R
import com.example.finalsubmissionrecylerviewsederhana.adapter.ArticleAdapter
import com.example.finalsubmissionrecylerviewsederhana.adapter.SavedAdapter
import com.example.finalsubmissionrecylerviewsederhana.utils.Resource
import com.example.finalsubmissionrecylerviewsederhana.utils.shareNews
import com.example.finalsubmissionrecylerviewsederhana.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlin.random.Random

class BookmarkFragment: Fragment(R.layout.fragment_bookmark) {
    lateinit var viewModel1 : NewsViewModel
    lateinit var newsAdapter: SavedAdapter
    val TAG = "BookmarkNews"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel1 = (context as MainActivity).viewModel


        setupRecyclerView()
        setViewModelObserver()


    }
    private fun setupRecyclerView() {
        newsAdapter = SavedAdapter()
        rycv_bookmark.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        newsAdapter.setOnclickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.actiong_bookmarkNewsFragment_toArticlFragment, bundle)

        }
        newsAdapter.setShareListener {
            shareNews(context, it)
        }
        val onItemHelperCallBack = object : ItemTouchHelper.SimpleCallback(

            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val  article = newsAdapter.differ.currentList[position]
                viewModel1.deleteArticle(article)

                Snackbar.make(requireView(),"Berhasil Menghapus",Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel1.insertArticle(article )
                    }
                    show()
                }

            }

        }
        ItemTouchHelper(onItemHelperCallBack).apply {
            attachToRecyclerView(rycv_bookmark)
        }
    }


    private fun setViewModelObserver() {

        viewModel1 = (activity as MainActivity).viewModel

        viewModel1.getSaveArticle().observe(viewLifecycleOwner, Observer {
            Log.i(TAG,""+it.size)
            newsAdapter.differ.submitList(it)
            rycv_bookmark.visibility = View.VISIBLE
            shimmer2FrameLayout.stopShimmerAnimation()
            shimmer2FrameLayout.visibility = View.GONE

        })
    }

}