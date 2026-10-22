package com.example.ch10.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = true          // JSON skema -> app/schemas/ (lihat build.gradle.kts)
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        const val NAME = "news_database"

        // TODO-DONE [T2.1] Hapus getInstance() setelah DatabaseModule dibuat.
        //  @Singleton di Hilt sudah menjamin hanya ada satu instance.
    }
}
