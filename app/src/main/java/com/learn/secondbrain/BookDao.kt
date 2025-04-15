package com.learn.secondbrain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface BookDao {

    // Fetch all books
    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<BookEntity>

    // Get book by ID
    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: Int): BookEntity?

    // Get books by category
    @Query("SELECT * FROM books WHERE category = :category")
    suspend fun getBooksByCategory(category: String): List<BookEntity>

    // Insert one book
    @Insert
    suspend fun insertBook(book: BookEntity)

    // Insert multiple books
    @Insert
    suspend fun insertAll(vararg books: BookEntity)

    // Optionally insert a list
    @Insert
    suspend fun insertBooks(books: List<BookEntity>)

    // Update a book
    @Update
    suspend fun updateBook(book: BookEntity)

    // Delete a specific book
    @Delete
    suspend fun deleteBook(book: BookEntity)

    // Delete all books
    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()

    // Search books by title or author (case-insensitive)
    @Query("SELECT * FROM books WHERE title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%'")
    suspend fun searchBooks(query: String): List<BookEntity>

    // Count total books
    @Query("SELECT COUNT(*) FROM books")
    suspend fun countBooks(): Int
}





