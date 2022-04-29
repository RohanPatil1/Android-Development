package com.rohan.news27.domain.repository

import com.rohan.news27.domain.models.Article

interface NewsArticleRepo {
    suspend fun getNewsArticle(): List<Article>
}