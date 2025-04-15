package com.learn.secondbrain

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: android.content.Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_database"
                )
                .fallbackToDestructiveMigration() // optional
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
