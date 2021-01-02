package com.example.completeexample.repository

import com.example.completeexample.model.Blog
import com.example.completeexample.retrofit.BlogRetrofit
import com.example.completeexample.retrofit.NetworkMapper
import com.example.completeexample.room.BlogDao
import com.example.completeexample.room.CacheMapper
import com.example.completeexample.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlogs(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlog = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlog)
            for (blog in blogs) {
                blogDao.insertBlog(cacheMapper.mapToEntity(blog))
            }
            val cacheBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cacheBlogs)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}