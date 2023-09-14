package com.example.quiz6.data.repository

import com.example.quiz6.data.model.UbikeInfo
import com.example.quiz6.data.util.Resource
import com.example.quiz6.domain.UbikeRepository
import retrofit2.Response

class UbikeRopositoryImpl(private val remoteDataSource: RemoteDataSource) : UbikeRepository {
    override suspend fun getUbikeInfo(): Resource<UbikeInfo> {
        return responseToResource(remoteDataSource.getUbikeInfo())
    }
}

private fun responseToResource(response: Response<UbikeInfo>):Resource<UbikeInfo>{
    if(response.isSuccessful){
        response.body()?.let {result->
            return Resource.Success(result)
        }
    }
    return Resource.Error(response.message())
}