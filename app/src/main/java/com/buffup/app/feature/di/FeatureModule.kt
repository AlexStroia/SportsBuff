package com.buffup.app.feature.di

import com.buffup.app.feature.di.module.viewModelModule
import org.koin.dsl.module

fun createFeatureModule() = viewModelModule()