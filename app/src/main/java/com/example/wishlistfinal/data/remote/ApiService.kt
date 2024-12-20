package com.example.wishlistfinal.data.remote

import com.example.wishlistfinal.data.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int
    ): BookResponse

    @GET("volumes/{id}")
    suspend fun getBookDetails(
        @Path("id") bookId: String
    ): BookResponse
} 