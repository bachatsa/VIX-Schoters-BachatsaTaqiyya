package com.example.finalsubmissionrecylerviewsederhana.service

import com.example.finalsubmissionrecylerviewsederhana.utils.Constants
import com.example.finalsubmissionrecylerviewsederhana.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
        @GET("v2/top-headlines")
        suspend fun getBreakingNews(
            @Query("country") country: String = "us",
            @Query("page") pageNumber: Int,
            @Query("apiKey") apiKey : String = Constants.API_KEY
        ) : Response<NewsResponse>

        @GET("v2/everything")
        suspend fun searchNews(
            @Query("q") country: String,
            @Query("page") pageNumber: Int,
            @Query("apiKey") apiKey : String = Constants.API_KEY
        ): Response<NewsResponse>
}