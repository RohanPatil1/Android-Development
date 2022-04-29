package com.rohan.news27.domain.use_cases

import com.rohan.news27.domain.models.Article
import com.rohan.news27.domain.repository.NewsArticleRepo
import com.rohan.news27.utils.response_handling.Resource
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow
import javax.inject.Inject

class GetNewsArticleUseCase @Inject constructor(private val getNewsArticleRepo: NewsArticleRepo) {

    operator fun invoke() = flow {
        emit(Resource.Loading(""))
        try {
            emit(Resource.Success(getNewsArticleRepo.getNewsArticle()))

        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }

    }

}