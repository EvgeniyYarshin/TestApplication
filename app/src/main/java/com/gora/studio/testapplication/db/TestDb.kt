package com.gora.studio.testapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gora.studio.testapplication.model.Json

@Database(
    entities = [Json::class],
    version = 1,
    exportSchema = false
)

abstract class TestDb : RoomDatabase() {
    abstract fun jsonDao(): JsonDao
}
