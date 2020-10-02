package com.buffup.app

import com.buffup.app.core.di.createCoreModules
import com.buffup.app.feature.di.createFeatureModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
class KoinModulesTest: KoinTest {

    @Test
    fun testFeatureModule() {
        startKoin { modules(createCoreModules(BuildConfig.BASE_URL) + createFeatureModule()) }
    }
}