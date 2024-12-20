package com.example.wishlistfinal.repository

import android.content.Context
import android.content.SharedPreferences

class UserRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun register(username: String, password: String, fullName: String): Boolean {
        if (prefs.contains("${username}_password")) return false
        prefs.edit()
            .putString("${username}_password", password)
            .putString("${username}_fullname", fullName)
            .apply()
        return true
    }

    fun login(username: String, password: String): Boolean {
        val storedPassword = prefs.getString("${username}_password", null)
        return if (storedPassword == password) {
            // Сохранить текущего пользователя
            prefs.edit().putString("current_user", username).apply()
            true
        } else {
            false
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.contains("current_user")
    }

    fun getCurrentUsername(): String? {
        return prefs.getString("current_user", null)
    }

    fun getCurrentUserFullName(): String? {
        val user = getCurrentUsername() ?: return null
        return prefs.getString("${user}_fullname", null)
    }

    fun logout() {
        prefs.edit().remove("current_user").apply()
    }
} 