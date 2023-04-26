package com.prabhakaran.nytimesapp.common.data.di

import com.prabhakaran.nytimesapp.common.data.network.ApiService
import com.prabhakaran.nytimesapp.features.business.ArticlesRepository
import com.prabhakaran.nytimesapp.features.business.ArticlesRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesPostRepository(
        service: ApiService
    ): ArticlesRepository = ArticlesRepositoryImp(service)

}