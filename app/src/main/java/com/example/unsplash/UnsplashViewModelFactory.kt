package com.example.unsplash

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unsplash.repo.UnsplashRepo

class UnsplashViewModelFactory(val app: Application, val unsplashRepo: UnsplashRepo ):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UnsplashViewModel(app,unsplashRepo) as T
    }
}