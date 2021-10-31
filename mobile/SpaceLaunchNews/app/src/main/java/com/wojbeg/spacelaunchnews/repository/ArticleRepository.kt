package com.wojbeg.spacelaunchnews.repository

import com.wojbeg.spacelaunchnews.api.ArticlesApi
import com.wojbeg.spacelaunchnews.db.ArticleDao
import com.wojbeg.spacelaunchnews.db.ArticleDatabase
//import com.wojbeg.spacelaunchnews.db.ArticleDao
//import com.wojbeg.spacelaunchnews.db.ArticleDatabase
import com.wojbeg.spacelaunchnews.models.Article
import com.wojbeg.spacelaunchnews.ui.NewsActivity
import retrofit2.Retrofit
import javax.inject.Inject

class ArticleRepository @Inject constructor (
    private val retrofitInstance: ArticlesApi,
) {

    suspend fun getArticles(limit: Int, startPoint: Int)=
        retrofitInstance.getArticles(limit, startPoint)


    suspend fun getSearchArticles(searchQuery: String, limit: Int, startPoint: Int)=
        retrofitInstance.getSearchArticles(searchQuery, limit, startPoint)

    suspend fun getArticlesCount()=
        retrofitInstance.getArticlesCount()

}