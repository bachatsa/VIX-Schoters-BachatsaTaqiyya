package com.example.finalsubmissionrecylerviewsederhana.control.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.finalsubmissionrecylerviewsederhana.service.RetrofitClient
import com.example.finalsubmissionrecylerviewsederhana.model.Article
import com.example.finalsubmissionrecylerviewsederhana.model.NewsResponse
import com.example.finalsubmissionrecylerviewsederhana.utils.Constants
import com.example.finalsubmissionrecylerviewsederhana.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DataSource(val scope : CoroutineScope) : PageKeyedDataSource<Int, Article>() {

    val breakingNews : MutableLiveData<MutableList<Article>> = MutableLiveData()
    val breakingPageNum  = 1
    var breakingnewsResponse : NewsResponse? = null


    val searchNews :MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNumber = 1
    var searchResponse : NewsResponse? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        scope.launch {
            try {
                val response = RetrofitClient.api.getBreakingNews("us",1,Constants.API_KEY)
                when{
                   response.isSuccessful -> {
                       response.body()?.articles?.let {
                           breakingNews.postValue(it)
                           callback.onResult(it,null,2)
                       }
                   }

                }
            } catch (excepetion:Exception){
                Log.e("ErrorDataSource : " , excepetion.message.toString())

            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        try {
            scope.launch {
                val response = RetrofitClient.api.getBreakingNews(
                    "us",
                    params.requestedLoadSize,
                    Constants.API_KEY
                )
                when {
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            callback.onResult(it, params.key + 1)
                        }
                    }
                }

            }
        }catch (excepetion:Exception){
            Log.e("ErrorDataSource : " , excepetion.message.toString())

        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

    }
}