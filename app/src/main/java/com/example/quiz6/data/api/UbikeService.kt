package com.example.quiz6.data.api

import com.example.quiz6.data.model.UbikeInfo
import retrofit2.Response
import retrofit2.http.GET

interface UbikeService {

    @GET("youbike_immediate.json")
    suspend fun getUbikeInfo() : Response<UbikeInfo>
}