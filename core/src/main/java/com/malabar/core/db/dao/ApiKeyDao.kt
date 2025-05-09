package com.malabar.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.malabar.core.db.data.ApiKey

@Dao
interface ApiKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(apiKey: ApiKey)

    @Query("SELECT * FROM api_key")
    suspend fun getApiKey(): ApiKey

    @Query("DELETE FROM api_key")
    suspend fun deleteApiKey()
}