package com.example.completeexample.di
import com.example.completeexample.repository.MainRepository
import com.example.completeexample.retrofit.BlogRetrofit
import com.example.completeexample.retrofit.NetworkMapper
import com.example.completeexample.room.BlogDao
import com.example.completeexample.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository{
        return MainRepository(
            blogDao,
            retrofit,
            cacheMapper,
            networkMapper
        )
    }
}