package com.example.unsplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.adapter.UnsplashAdapter
import com.example.unsplash.repo.UnsplashRepo
import com.example.unsplash.utils.Resource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var unsplashViewModel: UnsplashViewModel
    private lateinit var unsplashAdapter: UnsplashAdapter
    private var pageSize: Int = 8
    private var page: Int = 1
    val name = "ONO"
    var isLoading: Boolean = false
    var isLastpage: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repo = UnsplashRepo()
        val viewModelProviderFactory = UnsplashViewModelFactory(application,repo)
        unsplashViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(UnsplashViewModel::class.java)
        unsplashViewModel.getImageres(unsplashViewModel.ImagePage, pageSize)

        subscribeToObservers()
        setrecylerView()
    }

    private fun setrecylerView() {
        unsplashAdapter = UnsplashAdapter()
        recylerview.apply {
            adapter = unsplashAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            addOnScrollListener(scrollListener)
        }

    }

    val scrollListener =object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItem =layoutManager.findFirstVisibleItemPosition()
            val totalItemcount = layoutManager.itemCount
            val visibleItemCount = layoutManager.childCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastpage
            val isAtLastItem = firstVisibleItem+visibleItemCount >= totalItemcount
            val isNotAtTheBeginning =firstVisibleItem >=0
            val isTotalMoreThanVisible = totalItemcount >= pageSize
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtTheBeginning && isTotalMoreThanVisible
            if(shouldPaginate){
                page++
//                unsplashViewModel.ImagePage++
                Log.d(name,"view ${unsplashViewModel.ImagePage}")
                Log.d(name,"$page")
                unsplashViewModel.getImageres(unsplashViewModel.ImagePage,pageSize)
            }
        }
    }

    private fun subscribeToObservers() {
        unsplashViewModel.getImage.observe(this, {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let {
                        unsplashAdapter.differ.submitList(it)
                        if(it.size>0){
                            isLastpage = it.size<pageSize
                        }else isLastpage = true
                        if(isLastpage){
                            recylerview.setPadding(0,0,0,0)
                        }
                    }

                }
                is Resource.Error -> {
                    hideProgressBar()
                    it.message?.let { message ->
                        Toast.makeText(this, "NO Connection", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }
}