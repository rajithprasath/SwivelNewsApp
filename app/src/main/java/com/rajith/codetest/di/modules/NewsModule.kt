package com.rajith.codetest.di.modules

import com.rajith.codetest.database.NewsDatabase
import com.rajith.codetest.database.dao.NewsDao
import com.rajith.codetest.di.scope.FeatureScope
import com.rajith.codetest.mapper.NewsMapper
import com.rajith.codetest.mapper.NewsPresentationMapper
import com.rajith.codetest.network.repository.GetNewsUseCase
import com.rajith.codetest.network.service.NewsService
import com.rajith.codetest.network.source.NewsRemoteDataSource
import com.rajith.codetest.viewmodel.TopHeadlineViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class NewsModule {

    @Provides
    @FeatureScope
    fun provideNewseDao(db: NewsDatabase) : NewsDao = db.newsDao()

    @Provides
    @FeatureScope
    fun provideMapper() : NewsMapper = NewsMapper()

    @Provides
    @FeatureScope
    fun provideNewsService(retrofit: Retrofit) : NewsService = retrofit.create(NewsService::class.java)

    @Provides
    @FeatureScope
    fun provideRemoteDataSource(characterService: NewsService) = NewsRemoteDataSource(characterService)

    @Provides
    @FeatureScope
    fun provideCharacterListViewModelFactory(
        getNewsUseCase: GetNewsUseCase,
        mapper : NewsPresentationMapper
    ) = TopHeadlineViewModel.Factory(getNewsUseCase, mapper)
}