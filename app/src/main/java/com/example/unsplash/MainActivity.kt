package com.example.unsplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.unsplash.repo.UnsplashRepo
import com.example.unsplash.utils.Resource

class MainActivity : AppCompatActivity() {
    lateinit var unsplashViewModel: UnsplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repo = UnsplashRepo()
        val viewModelProviderFactory = UnsplashViewModelFactory(application,repo)
        unsplashViewModel =ViewModelProvider(this,viewModelProviderFactory).get(UnsplashViewModel::class.java)

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        unsplashViewModel.getImage.observe(this,{
            when(it){
                is Resource.Success->{

                }
            }
        })
    }
}