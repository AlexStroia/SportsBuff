package com.buffup.app.core.di

fun createCoreModules(baseUrl: String) = createNetworkModule(baseUrl) + createRepositoryModule()