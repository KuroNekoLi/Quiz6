package com.example.quiz6.data.model


import com.google.gson.annotations.SerializedName

data class UbikeInfoItem(
    @SerializedName("sarea")
    val sarea: String,
    @SerializedName("sna")
    val sna: String
)