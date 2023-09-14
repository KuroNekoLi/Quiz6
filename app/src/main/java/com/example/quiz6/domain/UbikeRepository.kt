package com.example.quiz6.domain

import com.example.quiz6.data.model.UbikeInfo
import com.example.quiz6.data.util.Resource

interface UbikeRepository {
    suspend fun getUbikeInfo() : Resource<UbikeInfo>
}