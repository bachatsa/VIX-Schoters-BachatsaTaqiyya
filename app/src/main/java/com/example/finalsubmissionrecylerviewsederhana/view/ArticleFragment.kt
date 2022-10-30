package com.example.finalsubmissionrecylerviewsederhana.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.finalsubmissionrecylerviewsederhana.MainActivity
import com.example.finalsubmissionrecylerviewsederhana.R
import kotlinx.android.synthetic.main.fragment_article.*
import kotlin.system.exitProcess

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel1: ViewModel
    private  val args : ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel1 = (activity as MainActivity).viewModel
        val article = args.article
        Log.d("articlesss",article.toString())

         Glide.with(this).load(article.urlToImage).into(imgArticle)
         tvTitleArticle.text = article.title
         tvNama.text = article.description
         tvLinkGithub.text = "Read More :\n"+ article.url

        tvLinkGithub.setOnClickListener {
            val uri = Uri.parse(article.url) // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
            exitProcess(-1)
        }


    }
}