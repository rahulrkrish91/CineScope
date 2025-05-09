package com.malabar.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.malabar.core.db.dao.ApiKeyDao
import com.malabar.core.db.data.ApiKey

@Database(entities = [ApiKey::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apiKeyDao(): ApiKeyDao
}