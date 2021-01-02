package com.example.completeexample.room
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BlogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlog(blogEntity: BlogCacheEntity): Long

    @Query("SELECT * FROM blog_table")
    suspend fun get(): List<BlogCacheEntity>
}