package com.example.wishlistfinal.repository

import androidx.lifecycle.LiveData
import com.example.wishlistfinal.data.local.BookDao
import com.example.wishlistfinal.data.model.Book
import com.example.wishlistfinal.data.remote.RetrofitInstance

class BookRepository(private val bookDao: BookDao) {

    val wishlist: LiveData<List<Book>> = bookDao.getAllBooks()

    suspend fun searchBooks(query: String) = RetrofitInstance.api.searchBooks(query)

    suspend fun addToWishlist(book: Book) = bookDao.insert(book)

    suspend fun removeFromWishlist(book: Book) = bookDao.delete(book)
} 