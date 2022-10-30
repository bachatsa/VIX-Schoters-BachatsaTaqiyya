package com.example.finalsubmissionrecylerviewsederhana.DI

import android.app.Application
import android.content.Context
import com.example.finalsubmissionrecylerviewsederhana.db.ArticleDatabase
import com.example.finalsubmissionrecylerviewsederhana.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsRepository(context: Context) : NewsRepository{
        return NewsRepository(ArticleDatabase(context))

    }
    @Provides
    @Singleton
    fun provideContext(application: Application) : Context{
        return application
    }


}