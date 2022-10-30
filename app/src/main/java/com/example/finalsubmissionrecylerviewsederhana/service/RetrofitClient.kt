package com.example.finalsubmissionrecylerviewsederhana.service

import com.example.finalsubmissionrecylerviewsederhana.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{
        private val retrofitClient by lazy {
            val loginInterceptor = HttpLoggingInterceptor()
            loginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loginInterceptor)
                .build()
            Retrofit.Builder().baseUrl(Constants.BASE_URL).
                                client(client).
                                  addConverterFactory(GsonConverterFactory.create()).
                                   build()

        }
        val api by lazy {
            retrofitClient.create(NewsApi::class.java)
        }
    }
}