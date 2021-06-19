package com.example.unsplash.remote

import com.example.unsplash.modal.UrlModal
import com.example.unsplash.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/topics/wallpapers/photos")
    suspend fun get(
        @Query("page")
        pageNumber:Int,
        @Query("per_page")
        per_page:Int
    ): Response<List<UrlModal>>
}