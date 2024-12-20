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
    private var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigateToFragment(BooksFragment(), "books")
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_books -> {
                    val isBackNavigation = currentFragmentTag == "wishlist"
                    navigateToFragment(BooksFragment(), "books", isBackNavigation)
                    true
                }
                R.id.nav_wishlist -> {
                    navigateToFragment(WishlistFragment(), "wishlist", false)
                    true
                }
                R.id.nav_login -> {
                    navigateToFragment(LoginFragment(), "login", false)
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToFragment(fragment: Fragment, tag: String, isBackNavigation: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()
        
        if (isBackNavigation) {
            transaction.setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        } else {
            transaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }

        transaction.replace(R.id.fragment_container, fragment, tag)
        transaction.commit()
        
        currentFragmentTag = tag
    }
} 