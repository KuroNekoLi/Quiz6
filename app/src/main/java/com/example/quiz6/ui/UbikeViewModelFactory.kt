package com.example.quiz6.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quiz6.data.repository.UbikeRopositoryImpl
import com.example.quiz6.domain.UbikeRepository
import javax.inject.Inject

class UbikeViewModelFactory @Inject constructor (private val app: Application,
                            private val uBikeRepository : UbikeRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UbikeViewModel(app, uBikeRepository) as T
    }
}