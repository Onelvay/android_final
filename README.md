# WishList - Android Book Discovery App

A modern Android application that helps users discover books and maintain their reading wishlist. Built with Kotlin and following MVVM architecture patterns.

## Features

- Search books using Google Books API
- View detailed book information including cover, title, and authors
- Add/remove books to personal wishlist
- Local user authentication
- Material Design UI with smooth animations
- Offline support for wishlist items

## Tech Stack

- **Language:** Kotlin
- **Architecture:** MVVM
- **UI Components:**
  - ViewBinding
  - RecyclerView
  - Material Design Components
  - Fragment Navigation
- **Local Storage:**
  - Room Database
  - SharedPreferences
- **Networking:**
  - Retrofit2
  - Glide for image loading
- **Async Operations:** Coroutines
- **Other Libraries:**
  - AndroidX
  - LiveData
  - ViewModel

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or newer
- JDK 17
- Android SDK (min SDK 24)

### Installation
1. Clone the repository
bash
git clone https://github.com/yourusername/wishlist.git

2. Open the project in Android Studio
3. Add your Google Books API key in `ApiService.kt`
4. Build and run the application

## Project Structure

![image](https://github.com/user-attachments/assets/9ed648c2-a617-4df8-9060-a10f1529580f)

## Architecture

The app follows MVVM architecture and Repository pattern:
- **View Layer:** Activities and Fragments
- **ViewModel:** Manages UI-related data and business logic
- **Repository:** Single source of truth for data
- **Room Database:** Local storage for wishlist
- **Retrofit:** Network operations

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Acknowledgments

- Google Books API
- Android 2024-2025 course teacher and assistansts
- Open source community
