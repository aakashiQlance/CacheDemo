package com.example.cachedemo.injection

import com.example.cachedemo.shareddata.api.ApiEndPoint
import com.example.cachedemo.shareddata.repo.RickAndMortyRepo
import com.example.cachedemo.shareddata.repo.RickAndMortyRepoImpl
import com.example.cachedemo.ui.viewmodel.MainViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    single<RickAndMortyRepo> { RickAndMortyRepoImpl(get()) }
    viewModel { MainViewModel(get()) }
}

val networkModule = module {
    single { provideHttpLogging() }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    val gson = GsonBuilder().setLenient().create()
    return Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
}

fun provideHttpLogging(): OkHttpClient {

    return OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
}

fun provideApiService(retrofit: Retrofit): ApiEndPoint {
    return retrofit.create(ApiEndPoint::class.java)
}

val appModules = viewModelModule + networkModule
