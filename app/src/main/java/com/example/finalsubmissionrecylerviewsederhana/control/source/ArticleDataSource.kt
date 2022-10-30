package com.example.finalsubmissionrecylerviewsederhana.control.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.finalsubmissionrecylerviewsederhana.model.Article
import kotlinx.coroutines.CoroutineScope

class ArticleDataSource(private val scope: CoroutineScope) :  androidx.paging.DataSource.Factory<Int,Article>(){
    val articleDataSourceFactory = MutableLiveData<com.example.finalsubmissionrecylerviewsederhana.control.source.DataSource>()
    override fun create(): DataSource<Int, Article> {
        val newArticleDataSource  = DataSource(scope)
        articleDataSourceFactory.postValue(newArticleDataSource)
        return newArticleDataSource
    }

}