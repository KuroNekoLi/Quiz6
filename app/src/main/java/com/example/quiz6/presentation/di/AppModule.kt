package com.example.quiz6.presentation.di

import com.example.quiz6.data.api.UbikeService
import com.example.quiz6.data.repository.RemoteDataSource
import com.example.quiz6.data.repository.RemoteDataSourceImpl
import com.example.quiz6.data.repository.UbikeRopositoryImpl
import com.example.quiz6.domain.UbikeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUbikeRepository(remoteDataSource: RemoteDataSource):UbikeRepository{
        return UbikeRopositoryImpl(remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        ubikeService: UbikeService
    ):RemoteDataSource{
        return RemoteDataSourceImpl(ubikeService)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://tcgbusfs.blob.core.windows.net/dotapp/youbike/v2/")
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsAPIService(retrofit: Retrofit):UbikeService{
        return retrofit.create(UbikeService::class.java)
    }
}