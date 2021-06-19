package com.example.unsplash.remote

import com.example.unsplash.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{

        private val retrofit by lazy<Retrofit>{
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }


        val api:UnsplashApi by lazy {
            retrofit.create(UnsplashApi::class.java)
        }
    }
}