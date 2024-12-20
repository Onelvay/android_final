package com.example.wishlistfinal.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wishlistfinal.data.model.Book
import com.example.wishlistfinal.repository.BookRepository
import com.example.wishlistfinal.utils.Resource
import kotlinx.coroutines.launch

class BooksViewModel(private val repository: BookRepository) : ViewModel() {

    private val _searchResults = MutableLiveData<Resource<List<Book>>>()
    val searchResults: LiveData<Resource<List<Book>>> = _searchResults

    fun searchBooks(query: String) {
        viewModelScope.launch {
            _searchResults.value = Resource.Loading()
            try {
                val response = repository.searchBooks(query)
                val books = response.items?.map { item ->
                    Book(
                        id = item.id,
                        title = item.volumeInfo?.title ?: "",
                        authors = item.volumeInfo?.authors?.joinToString(", ") ?: "",
                        description = item.volumeInfo?.description ?: "",
                        imageUrl = item.volumeInfo?.imageLinks?.thumbnail ?: ""
                    )
                } ?: emptyList()
                _searchResults.value = Resource.Success(books)
            } catch (e: Exception) {
                _searchResults.value = Resource.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun addToWishlist(book: Book) {
        viewModelScope.launch {
            repository.addToWishlist(book)
        }
    }
}

class BooksViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BooksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BooksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 