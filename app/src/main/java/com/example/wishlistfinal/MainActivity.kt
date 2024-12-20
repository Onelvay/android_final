package com.example.wishlistfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.wishlistfinal.databinding.ActivityMainBinding
import com.example.wishlistfinal.ui.authentication.LoginFragment
import com.example.wishlistfinal.ui.books.BooksFragment
import com.example.wishlistfinal.ui.wishlist.WishlistFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start with LoginFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, LoginFragment())
                .commit()
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_books -> {
                    navigateToFragment(BooksFragment())
                    true
                }
                R.id.nav_wishlist -> {
                    navigateToFragment(WishlistFragment())
                    true
                }
                R.id.nav_login -> {
                    navigateToFragment(LoginFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(binding.fragmentContainer.id, fragment)
            .addToBackStack(null)
            .commit()
    }
} 