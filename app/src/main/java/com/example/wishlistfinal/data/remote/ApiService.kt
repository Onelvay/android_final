package com.example.wishlistfinal.data.remote

import com.example.wishlistfinal.data.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String = "AIzaSyCeL0OTD3_6pzbQIlNRTfGzEnqcgT64Xps"
    ): BookResponse
} 