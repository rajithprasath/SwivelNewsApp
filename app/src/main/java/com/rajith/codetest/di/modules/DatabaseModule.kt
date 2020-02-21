package com.rajith.codetest.di.modules

import android.content.Context
import androidx.room.Room
import com.rajith.codetest.database.NewsDatabase
import com.rajith.codetest.util.Constant
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context) : NewsDatabase = Room.databaseBuilder(context, NewsDatabase::class.java, Constant.DATABASE_NAME)
        .build()
}