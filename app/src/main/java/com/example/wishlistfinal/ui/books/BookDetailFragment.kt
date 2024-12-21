package com.example.wishlistfinal.ui.books

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.wishlistfinal.data.model.Book
import com.example.wishlistfinal.databinding.FragmentBookDetailBinding

class BookDetailFragment : Fragment() {
    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("BookDetailFragment", "onViewCreated")
        arguments?.getParcelable<Book>("book")?.let { book ->
            Log.d("BookDetailFragment", "Book received: ${book.title}")
            setupBookDetails(book)
        } ?: run {
            Log.e("BookDetailFragment", "No book data received")
        }
    }

    private fun setupBookDetails(book: Book) {
        binding.apply {
            tvTitle.text = book.title
            tvAuthors.text = book.authors
            tvDescription.text = book.description

            Glide.with(requireContext())
                .load(book.imageUrl.replace("http://", "https://"))
                .into(ivBookCover)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(book: Book) = BookDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("book", book)
            }
        }
    }
} 