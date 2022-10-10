package com.example.cachedemo

import android.app.Application
import com.example.cachedemo.common.cache.SimpleMortyCache
import com.example.cachedemo.injection.appModules
import com.example.cachedemo.injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RickAndMortyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RickAndMortyApplication)
            modules(appModules)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}