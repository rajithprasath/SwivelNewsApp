package com.rajith.codetest.di.modules

import com.rajith.codetest.model.NewsRepositoryImpl
import com.rajith.codetest.network.repository.NewsRepository
import dagger.Binds
import dagger.Module


@Module
interface RepositoryModule {
    @Binds
    fun bindProjectRepository(projectRepository: NewsRepositoryImpl): NewsRepository
}