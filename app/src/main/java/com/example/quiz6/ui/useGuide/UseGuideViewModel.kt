package com.example.quiz6.ui.useGuide

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UseGuideViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "使用說明"
    }
    val text: LiveData<String> = _text
}