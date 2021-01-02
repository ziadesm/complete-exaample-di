package com.example.completeexample.di
import android.content.Context
import androidx.room.Room
import com.example.completeexample.room.BlogDatabase
import com.example.completeexample.room.BlogDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BlogDatabase{
        return Room.databaseBuilder(context,
            BlogDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBlogDao(db: BlogDatabase) = db.dao()
}