package com.example.bathingsitev2

import android.content.Context
import androidx.room.Room
import com.example.bathingsitev2.database.AppDatabase
import com.example.bathingsitev2.database.BathingSiteDao
import com.example.bathingsitev2.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BathingSiteModule {


    @Singleton
    @Provides
    fun getRepository(dao:BathingSiteDao):Repository{
        return Repository(dao)
    }

    @Singleton
    @Provides
    fun getDao(database: AppDatabase): BathingSiteDao {
        return database.bathingSiteDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(
            context.applicationContext,AppDatabase::class.java,"app_database"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}