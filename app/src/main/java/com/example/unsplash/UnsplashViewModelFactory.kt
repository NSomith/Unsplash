package com.example.unsplash

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unsplash.repo.UnsplashRepo
import com.example.unsplash.ui.UnsplashViewModel

class UnsplashViewModelFactory(val application: Application,val unsplashRepo: UnsplashRepo ):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UnsplashViewModel(application,unsplashRepo) as T
    }
}