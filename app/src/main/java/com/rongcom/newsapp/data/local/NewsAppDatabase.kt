package com.rongcom.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rongcom.newsapp.data.local.dao.SourceDao
import com.rongcom.newsapp.data.local.dao.TopHeadlinesDao
import com.rongcom.newsapp.data.local.entity.NewsSources
import com.rongcom.newsapp.data.local.entity.TopHeadlineEntity

@Database(
    entities = [TopHeadlineEntity::class, NewsSources::class],
    version = 1,
    exportSchema = false
)
abstract class NewsAppDatabase : RoomDatabase(){
    abstract fun topHeadlinesDao(): TopHeadlinesDao
    abstract fun newsSourceDao(): SourceDao
}