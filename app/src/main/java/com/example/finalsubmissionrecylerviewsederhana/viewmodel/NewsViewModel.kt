package com.example.finalsubmissionrecylerviewsederhana.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.example.finalsubmissionrecylerviewsederhana.model.Article
import com.example.finalsubmissionrecylerviewsederhana.model.NewsResponse
import com.example.finalsubmissionrecylerviewsederhana.repository.NewsRepository
import com.example.finalsubmissionrecylerviewsederhana.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class  NewsViewModel @Inject constructor(val newsRepository: NewsRepository)
    : ViewModel() {
    val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingPageNum  = 1
    var breakingnewsResponse : NewsResponse? = null

    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNumber = 1
    var searchResponse : NewsResponse? = null

    lateinit var article: LiveData<PagedList<Article>>
    init {
       getBreakingNews("us")
        }
    fun getBreakingNews(countryCode : String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingPageNum)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>?{
        if (response.isSuccessful){
            response.body()?.let { newsResponse ->
                breakingPageNum++
                if (breakingnewsResponse == null){
                    breakingnewsResponse = newsResponse
                } else{
                    val oldArticle = breakingnewsResponse?.articles
                    val newArticles = newsResponse.articles
                    oldArticle?.addAll(newArticles)
                }
                return Resource.Succes(breakingnewsResponse ?: newsResponse)
            }
        }
        return Resource.Error(response.message())
    }
    fun getSearchNews(queryString: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val searchNewsResponse = newsRepository.searchNews(queryString, searchNumber)
        searchNews.postValue(handleSearchNewsResponse(searchNewsResponse))
    }
    fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                searchNumber++
                if (searchNumber == null){
                    searchResponse == resultResponse
                }
                else{
                    val oldItem = searchResponse?.articles
                    val newArticle = resultResponse.articles
                    oldItem?.addAll(newArticle)
                }
                return Resource.Succes(searchResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())

    }
    fun insertArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }
    fun deleteArticle(article: Article) =  viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
    fun getSaveArticle() = newsRepository.getSavedNews()

    fun getBreakingNews() :LiveData<PagedList<Article>>{
        return article
    }


}