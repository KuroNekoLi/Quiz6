package com.example.quiz6.ui.payWay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PayWayViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "付費方式"
    }
    val text: LiveData<String> = _text
}