package com.rohan.news27.utils.mappers

import com.rohan.news27.data.model.ArticleDTO
import com.rohan.news27.domain.models.Article

fun List<ArticleDTO>.toDomain(): List<Article> {
    return map {
        Article(
            content = it.content ?: "",
            description = it.description ?: "",
            title = it.title ?: "",
            urlToImage = it.urlToImage ?: "",
        )
    }
}