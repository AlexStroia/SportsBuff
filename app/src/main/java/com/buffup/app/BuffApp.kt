package com.buffup.app

import android.app.Application
import com.buffup.app.core.di.createCoreModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

@ExperimentalCoroutinesApi
class BuffApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BuffApp)
            modules(loadCoreModules())
        }
    }

    fun loadCoreModules(): List<Module> = createCoreModules(BuildConfig.BASE_URL).also {
        loadKoinModules(it)
    }
}