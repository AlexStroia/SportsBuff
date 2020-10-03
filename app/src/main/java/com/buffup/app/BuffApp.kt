package com.buffup.app

import android.app.Application
import com.buffup.app.core.di.createCoreModules
import com.buffup.app.feature.di.createFeatureModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@ExperimentalCoroutinesApi
class BuffApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@BuffApp)
            modules(createCoreModules(BuildConfig.BASE_URL) + createFeatureModule())
        }
    }
}