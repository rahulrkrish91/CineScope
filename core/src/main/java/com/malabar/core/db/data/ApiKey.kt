package com.malabar.core.db.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "api_key")
data class ApiKey(
    @PrimaryKey val id: Int = 0,
    val key: String
)
