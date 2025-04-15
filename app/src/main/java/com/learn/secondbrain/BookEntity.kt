package com.learn.secondbrain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val summary: String,
    val imageRes: Int, // Consider switching to String for URLs/paths in the future
    val category: String = "Uncategorized"
)