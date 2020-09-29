package com.buffup.app.core.di

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun createCoreModules(baseUrl: String) =
    createNetworkModule(baseUrl) + createRepositoryModule() + createUseCaseModule()