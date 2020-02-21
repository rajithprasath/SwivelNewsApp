package com.rajith.codetest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rajith.codetest.database.dao.NewsDao
import com.rajith.codetest.database.entity.NewsEntity


@Database(entities = [NewsEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

}