package com.example.wishlistfinal.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.wishlistfinal.R
import com.example.wishlistfinal.databinding.FragmentProfileBinding
import com.example.wishlistfinal.repository.UserRepository

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        userRepository = UserRepository(requireContext())

        val fullName = userRepository.getCurrentUserFullName() ?: "No Name"
        val email = userRepository.getCurrentUsername() ?: "No Email"


        binding.etFullName.setText(fullName)
        binding.etEmail.setText(email)

        binding.btnSaveProfile.setOnClickListener {
            val newFullName = binding.etFullName.text.toString()
            val newEmail = binding.etEmail.text.toString()
            if (newFullName.isNotEmpty() && newEmail.isNotEmpty()) {
                Toast.makeText(requireContext(), "Profile updated!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                userRepository.logout()
                Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, com.example.wishlistfinal.ui.authentication.LoginFragment())
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
