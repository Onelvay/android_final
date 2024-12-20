package com.example.wishlistfinal.data.remote

import com.example.wishlistfinal.data.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String = "AIzaSyAoriFQBU93K4kd9NOZgYZMh_BijwL6sdQ"  // Replace with your Google Books API key
    ): BookResponse
} 