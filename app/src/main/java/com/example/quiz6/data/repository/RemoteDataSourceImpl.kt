package com.example.quiz6.data.repository

import com.example.quiz6.data.api.UbikeService
import com.example.quiz6.data.model.UbikeInfo
import retrofit2.Response

class RemoteDataSourceImpl(private val ubikeService: UbikeService):RemoteDataSource {
    override suspend fun getUbikeInfo(): Response<UbikeInfo> {
        return ubikeService.getUbikeInfo()
    }
}