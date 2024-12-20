package com.example.wishlistfinal.repository

import android.content.Context
import android.content.SharedPreferences

class UserRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun register(username: String, password: String): Boolean {
        if (prefs.contains(username)) return false
        prefs.edit().putString(username, password).apply()
        return true
    }

    fun login(username: String, password: String): Boolean {
        val storedPassword = prefs.getString(username, null)
        return storedPassword == password
    }
} 