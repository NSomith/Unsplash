package com.example.unsplash.repo

import com.example.unsplash.remote.RetrofitInstance

class UnsplashRepo {
    suspend fun getTopicsWallpaper(pageNumber:Int,per_page:Int) =
        RetrofitInstance.api.get(pageNumber,per_page)
}