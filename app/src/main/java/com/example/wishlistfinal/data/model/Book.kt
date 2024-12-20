package com.example.wishlistfinal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    val id: String,
    val title: String,
    val authors: String,
    val description: String,
    val imageUrl: String
) 