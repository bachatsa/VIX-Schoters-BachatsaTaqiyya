package com.example.finalsubmissionrecylerviewsederhana.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finalsubmissionrecylerviewsederhana.model.Article

@Database(
    entities = [Article::class],
    version = 3
)
@TypeConverters(Convertes::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticlesDAO() : ArticleDAO
    companion object{
        @Volatile
        private var articleDBInstance : ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = articleDBInstance ?: synchronized(LOCK){
            articleDBInstance ?: createDatabaseInstance(context).also{
                articleDBInstance = it
            }
        }
        private fun  createDatabaseInstance(context: Context) =
            Room.databaseBuilder(context, ArticleDatabase::class.java,
                "articles_db.db")
                .fallbackToDestructiveMigration().build()
    }


}