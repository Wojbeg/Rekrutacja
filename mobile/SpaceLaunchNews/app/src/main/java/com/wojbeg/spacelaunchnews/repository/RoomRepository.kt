package com.wojbeg.spacelaunchnews.repository

import com.wojbeg.spacelaunchnews.db.ArticleDao
import com.wojbeg.spacelaunchnews.db.ArticleDatabase
import com.wojbeg.spacelaunchnews.models.Article
import javax.inject.Inject

class RoomRepository @Inject constructor (
    private val articleDatabase: ArticleDatabase
) {

    private val articleDao: ArticleDao = articleDatabase.getArticleDao()

    suspend fun insert(article: Article) =
        articleDao.insert(article)

    fun getSavedNews() =
        articleDao.getSavedArticles()

    suspend fun deleteArticle(article: Article) =
        articleDao.deleteArticle(article)


}