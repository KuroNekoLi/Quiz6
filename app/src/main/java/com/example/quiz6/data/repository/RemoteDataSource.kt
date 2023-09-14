package com.example.quiz6.data.repository

import com.example.quiz6.data.model.UbikeInfo
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getUbikeInfo() : Response<UbikeInfo>
}