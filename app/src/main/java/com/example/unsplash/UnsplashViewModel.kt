package com.example.unsplash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.unsplash.modal.UrlModal
import com.example.unsplash.repo.UnsplashRepo
import com.example.unsplash.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class UnsplashViewModel(val app: Application, val unsplashRepo: UnsplashRepo):AndroidViewModel(app) {

    val getImage : MutableLiveData<Resource<List<UrlModal>>> = MutableLiveData()
    var ImagePage = 1
    var breakingNewsResonse:UrlModal? = null

    fun getImageres(page:Int,per_page:Int) = viewModelScope.launch {
        safefetch(page,per_page)
    }

    private suspend fun safefetch(page: Int, perPage: Int) {
        getImage.postValue(Resource.Loading())
        try {
//            if(hasInternetConnection()){
//                val response =  unsplashRepo.getTopicsWallpaper(page,perPage)
//                getImage.postValue(handlesafe(response))
//            }else{
//                getImage.postValue(Resource.Error("NO INTERNET CONNECTION"))
//            }
            val response =  unsplashRepo.getTopicsWallpaper(page,perPage)
            getImage.postValue(handlesafe(response))
        }catch (t: Throwable){
            when(t){
                is IOException -> getImage.postValue(Resource.Error("Network Failure"))
                else -> getImage.postValue(Resource.Error("COnversion Error"))
            }
        }
    }

    private fun handlesafe(response: Response<List<UrlModal>>): Resource<List<UrlModal>>? {
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

//    private fun hasInternetConnection(): Boolean {
//
//    }

}