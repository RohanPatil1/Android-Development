package com.rohan.news27.presentation

import com.rohan.news27.domain.models.Article

data class HomeStateHolder(
    val isLoading: Boolean = false,
    val data: List<Article>? = null,
    val error: String = ""


)
