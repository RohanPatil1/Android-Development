package com.rohan.news27.data.repository

import com.rohan.news27.data.network.ApiService
import com.rohan.news27.domain.models.Article
import com.rohan.news27.domain.repository.NewsArticleRepo
import com.rohan.news27.utils.mappers.toDomain
import com.rohan.news27.utils.response_handling.SafeApiRequest
import javax.inject.Inject

class NewsArticleRepoImpl @Inject constructor(private val apiService: ApiService) :
    NewsArticleRepo, SafeApiRequest() {
    override suspend fun getNewsArticle(): List<Article> {
        val response = safeApiRequest { apiService.getArticles() }
        return response.articles?.toDomain()!!
    }
}