package com.rohan.news27.domain.models

import com.rohan.news27.data.model.SourceDTO

data class Article(

    val content: String,
    val description: String,
    val title: String,
    val urlToImage: String

)