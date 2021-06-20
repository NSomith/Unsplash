package com.example.unsplash

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplash.modal.UrlModal
import com.example.unsplash.repo.UnsplashRepo
import com.example.unsplash.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class UnsplashViewModel(val app:Application,val unsplashRepo: UnsplashRepo):ViewModel() {

    val getImage : MutableLiveData<Resource<List<UrlModal>>> = MutableLiveData()
    var ImagePage = 1
    var getListImages:MutableList<UrlModal>? = null

    fun getImageres(page:Int,per_page:Int) = viewModelScope.launch {
        safefetch(page,per_page)
    }


    private suspend fun safefetch(page: Int, perPage: Int) {
        getImage.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()){
                val response =  unsplashRepo.getTopicsWallpaper(page,perPage)
                getImage.postValue(handlesafe(response))
            }else{
                getImage.postValue(Resource.Error("NO INTERNET CONNECTION"))
            }
//            val response =  unsplashRepo.getTopicsWallpaper(page,perPage)
//            getImage.postValue(handlesafe(response))
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
                ImagePage++
                if(getListImages == null){
                    getListImages =it.toMutableList()
                }else{
                    val oldimg = getListImages
                    val newimg = it
                    oldimg?.addAll(newimg)
                }
                return Resource.Success(getListImages?:it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection(): Boolean {

        val connitivityManger = app.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connitivityManger.activeNetwork ?:return false
            val capabilities = connitivityManger.getNetworkCapabilities(activeNetwork)?:return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->true
                else -> false
            }
        }else{
            connitivityManger.activeNetworkInfo.run {
                return when(this?.type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET ->true
                    else ->false
                }
            }
        }
        return false
    }

}