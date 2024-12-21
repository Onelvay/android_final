package com.example.wishlistfinal.ui.books

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wishlistfinal.data.local.AppDatabase
import com.example.wishlistfinal.databinding.FragmentBooksBinding
import com.example.wishlistfinal.repository.BookRepository
import com.example.wishlistfinal.ui.adapters.BooksAdapter
import com.example.wishlistfinal.utils.Resource

class BooksFragment : Fragment() {

    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BooksViewModel
    private lateinit var adapter: BooksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBooksBinding.inflate(inflater, container, false)
        
        setupViewModel()
        setupRecyclerView()
        setupSearch()
        setupObservers()

        // Test search immediately
        viewModel.searchBooks("harry potter")
        
        return binding.root
    }

    private fun setupViewModel() {
        val dao = AppDatabase.getDatabase(requireContext()).bookDao()
        val repository = BookRepository(dao)
        val factory = BooksViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[BooksViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = BooksAdapter(
            onAddToWishlist = { book ->
                viewModel.addToWishlist(book)
                Toast.makeText(requireContext(), "Book added to wishlist", Toast.LENGTH_SHORT).show()
            },
            onItemClick = { book ->
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                    .replace(R.id.fragment_container, BookDetailFragment.newInstance(book))
                    .addToBackStack(null)
                    .commit()
            }
        )
        binding.recyclerViewBooks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewBooks.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.searchResults.observe(viewLifecycleOwner) { resource ->
            Log.d("BooksFragment", "Received resource: $resource")
            when (resource) {
                is Resource.Loading -> {
                    Log.d("BooksFragment", "Loading...")
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("BooksFragment", "Success! Books: ${resource.data?.size}")
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    Log.d("BooksFragment", "Error: ${resource.message}")
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupSearch() {
        binding.etSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = v.text.toString()
                Log.d("BooksFragment", "Searching for: $query")
                if (query.isNotEmpty()) {
                    viewModel.searchBooks(query)
                }
                true
            } else {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
