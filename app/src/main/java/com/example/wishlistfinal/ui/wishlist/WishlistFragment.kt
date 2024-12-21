package com.example.wishlistfinal.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wishlistfinal.R
import com.example.wishlistfinal.data.local.AppDatabase
import com.example.wishlistfinal.databinding.FragmentWishlistBinding
import com.example.wishlistfinal.repository.BookRepository
import com.example.wishlistfinal.repository.UserRepository
import com.example.wishlistfinal.ui.adapters.BooksAdapter

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WishlistViewModel
    private lateinit var adapter: BooksAdapter
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        userRepository = UserRepository(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!userRepository.isLoggedIn()) {
            Toast.makeText(requireContext(), "Please log in to access Wishlist", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, com.example.wishlistfinal.ui.authentication.LoginFragment())
                .commit()
            return
        }

        val dao = AppDatabase.getDatabase(requireContext()).bookDao()
        val repository = BookRepository(dao)
        val factory = WishlistViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(WishlistViewModel::class.java)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = BooksAdapter(
            onAddToWishlist = { book ->
                viewModel.removeFromWishlist(book)
                Toast.makeText(requireContext(), "Book removed from wishlist", Toast.LENGTH_SHORT).show()
            },
            onItemClick = { book ->
                val detailFragment = BookDetailFragment.newInstance(book)
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
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
