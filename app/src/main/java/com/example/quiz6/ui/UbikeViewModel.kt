package com.example.quiz6.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quiz6.data.model.UbikeInfo
import com.example.quiz6.data.repository.UbikeRopositoryImpl
import com.example.quiz6.data.util.Resource
import com.example.quiz6.domain.UbikeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UbikeViewModel(
    private val app: Application,
    private val uBikeRepository : UbikeRepository
): AndroidViewModel(app) {

    val uBikeInfoLiveData : MutableLiveData<Resource<UbikeInfo>> = MutableLiveData()
    private val _searchHistoryLiveData = MutableLiveData<List<String>>(emptyList())
    val searchHistoryLiveData: LiveData<List<String>>
        get() = _searchHistoryLiveData

    fun getUbikeInfo() = viewModelScope.launch(Dispatchers.IO) {
        uBikeInfoLiveData.postValue(Resource.Loading())

        try {
            if (isNetworkAvailable(app)){
                val apiResult = uBikeRepository.getUbikeInfo()
                uBikeInfoLiveData.postValue(apiResult)
            }else{
                uBikeInfoLiveData.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            uBikeInfoLiveData.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun addToSearchHistory(searchResult: String) {
        val currentList = _searchHistoryLiveData.value ?: emptyList()
        val updatedList = currentList.toMutableList().apply {
            add(0, searchResult)
        }.distinct()

        _searchHistoryLiveData.value = updatedList
    }

    private fun isNetworkAvailable(context: Context?) : Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}