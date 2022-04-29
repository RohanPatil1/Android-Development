package com.rohan.news27.domain.di

import com.rohan.news27.data.network.ApiService
import com.rohan.news27.data.repository.NewsArticleRepoImpl
import com.rohan.news27.domain.repository.NewsArticleRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    fun provideNewsRepo(apiService: ApiService): NewsArticleRepo {
        return NewsArticleRepoImpl(apiService)
    }


}