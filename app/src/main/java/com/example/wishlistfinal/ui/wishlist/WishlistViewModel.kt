package com.example.wishlistfinal.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wishlistfinal.data.model.Book
import com.example.wishlistfinal.repository.BookRepository
import kotlinx.coroutines.launch

class WishlistViewModel(private val repository: BookRepository) : ViewModel() {

    val wishlist: LiveData<List<Book>> = repository.wishlist

    fun removeFromWishlist(book: Book) {
        viewModelScope.launch {
            repository.removeFromWishlist(book)
        }
    }
}

class WishlistViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishlistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WishlistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 