package com.example.wishlistfinal.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wishlistfinal.data.local.AppDatabase
import com.example.wishlistfinal.databinding.FragmentWishlistBinding
import com.example.wishlistfinal.repository.BookRepository
import com.example.wishlistfinal.ui.adapters.BooksAdapter

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WishlistViewModel
    private lateinit var adapter: BooksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        val dao = AppDatabase.getDatabase(requireContext()).bookDao()
        val repository = BookRepository(dao)
        val factory = WishlistViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(WishlistViewModel::class.java)

        setupRecyclerView()
        setupObservers()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = BooksAdapter { book ->
            viewModel.removeFromWishlist(book)
        }
        binding.recyclerViewWishlist.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewWishlist.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.wishlist.observe(viewLifecycleOwner) { books ->
            adapter.submitList(books)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}