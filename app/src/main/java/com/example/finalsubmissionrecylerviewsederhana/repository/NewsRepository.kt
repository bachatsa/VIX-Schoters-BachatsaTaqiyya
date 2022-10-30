package com.example.finalsubmissionrecylerviewsederhana.repository

import com.example.finalsubmissionrecylerviewsederhana.service.RetrofitClient
import com.example.finalsubmissionrecylerviewsederhana.db.ArticleDatabase
import com.example.finalsubmissionrecylerviewsederhana.model.Article

class NewsRepository( var db : ArticleDatabase){

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitClient.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitClient.api.searchNews(searchQuery, pageNumber)

    suspend fun upsert(article : Article) = db.getArticlesDAO().insert(article)

    fun getSavedNews() = db.getArticlesDAO().getArticles()

    suspend fun deleteArticle(article : Article) = db.getArticlesDAO().deleteArticles(article)

}