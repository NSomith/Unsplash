package com.example.unsplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.unsplash.adapter.UnsplashAdapter
import com.example.unsplash.repo.UnsplashRepo
import com.example.unsplash.utils.Resource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var unsplashViewModel: UnsplashViewModel
    lateinit var unsplashAdapter: UnsplashAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repo = UnsplashRepo()
        val viewModelProviderFactory = UnsplashViewModelFactory(application,repo)
        unsplashViewModel =ViewModelProvider(this,viewModelProviderFactory).get(UnsplashViewModel::class.java)

        subscribeToObservers()
        setrecylerView()
    }

    private fun setrecylerView() {
        unsplashAdapter = UnsplashAdapter()
        recylerview.apply {
            adapter = unsplashAdapter
            layoutManager = GridLayoutManager(this@MainActivity,2)
        }
    }

    private fun subscribeToObservers() {
        unsplashViewModel.getImage.observe(this,{
            when(it){
                is Resource.Success->{
                    it.data?.let {
                        unsplashAdapter.differ.submitList(it)
                    }
                }
                is Resource.Error->{

                }
                is Resource.Loading->{

                }
            }
        })
    }
}