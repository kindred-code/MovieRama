package com.mpolitakis.movierama


import com.mpolitakis.movierama.networking.ApiService
import com.mpolitakis.movierama.repository.popular.PopularMoviesRepository
import com.mpolitakis.movierama.repository.popular.PopularMoviesRepositoryImpl
import com.mpolitakis.movierama.repository.search.SearchedMoviesRepository
import com.mpolitakis.movierama.repository.search.SearchedMoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AppModule{

    @Provides
    fun providePopularMoviesRepository(apiService: ApiService) : PopularMoviesRepository =
        PopularMoviesRepositoryImpl(apiService)

    @Provides
    fun provideSearchedMoviesRepository(apiService: ApiService): SearchedMoviesRepository =
        SearchedMoviesRepositoryImpl(apiService)

}